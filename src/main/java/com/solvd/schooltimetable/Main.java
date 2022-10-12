package com.solvd.schooltimetable;

import com.solvd.schooltimetable.domain.CalendarDay;
import com.solvd.schooltimetable.domain.SchoolClass;
import com.solvd.schooltimetable.domain.SchoolTimetable;
import com.solvd.schooltimetable.domain.Teacher;
import com.solvd.schooltimetable.genetic.GeneticAlgo;
import com.solvd.schooltimetable.genetic.GeneticAlgoConfig;
import com.solvd.schooltimetable.service.CalendarDayService;
import com.solvd.schooltimetable.service.SchoolClassService;
import com.solvd.schooltimetable.service.SchoolTimetableService;
import com.solvd.schooltimetable.service.TeacherService;
import com.solvd.schooltimetable.service.impl.CalendarDayServiceImpl;
import com.solvd.schooltimetable.service.impl.SchoolClassServiceImpl;
import com.solvd.schooltimetable.service.impl.SchoolTimetableServiceImpl;
import com.solvd.schooltimetable.service.impl.TeacherServiceImpl;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.IntStream;

public class Main {

    private static final Logger LOGGER = LogManager.getLogger(Main.class);

    public static void main(String[] args) {
        TeacherService teacherService = new TeacherServiceImpl();
        CalendarDayService calendarDayService = new CalendarDayServiceImpl();
        SchoolClassService schoolClassService = new SchoolClassServiceImpl();
        SchoolTimetableService schoolTimetableService = new SchoolTimetableServiceImpl();

        List<SchoolClass> schoolClasses = schoolClassService.findAll();
        List<Teacher> teachers = teacherService.findAll();
        List<CalendarDay> calendarDays = calendarDayService.findAll();

        GeneticAlgoConfig geneticAlgoConfig = new GeneticAlgoConfig("geneticAlgoConfig.properties");
        int currentHashcode = Objects.hash(
                teachers,
                schoolClasses,
                calendarDays,
                geneticAlgoConfig.getMinWorkDays(),
                geneticAlgoConfig.getMinLessons(),
                geneticAlgoConfig.getMaxLessons());
        Optional<SchoolTimetable> loadedSchoolTimetable = schoolTimetableService.findAll().stream()
                .filter(schoolTimetable -> schoolTimetable.getHashcode() == currentHashcode)
                .findFirst();
        if (loadedSchoolTimetable.isPresent()) {
            LOGGER.info("School time table loaded from database: ");
            LOGGER.info(loadedSchoolTimetable.get().toTableString());
        }
        else {
            int n = 128;
            long t = System.currentTimeMillis();
            LOGGER.info("Number of classes: " + schoolClasses.size());
            Optional<SchoolTimetable> generated =
                    IntStream.range(0, n)
                            .parallel()
                            .boxed()
                            .map(i -> {
                                GeneticAlgo geneticAlgoThread = new GeneticAlgo(geneticAlgoConfig);
                                geneticAlgoThread.setSchoolClasses(schoolClasses);
                                geneticAlgoThread.setTeachers(teachers);
                                geneticAlgoThread.setCalendarDays(calendarDays);
                                geneticAlgoThread.run();
                                return geneticAlgoThread;
                            })
                            .filter(GeneticAlgo::isGood)
                            .peek(GeneticAlgo::generateAndSetHashcode)
                            .map(GeneticAlgo::getCurrentBest)
                            .findAny();
            LOGGER.info("Elapsed time: " + (System.currentTimeMillis() - t) / 1000.);
            LOGGER.info(generated
                    .orElseThrow(() -> new RuntimeException("Unable to generate timetable with such arguments."))
                    .toTableString());
            schoolTimetableService.create(generated.get());
        }
    }
}
