package com.solvd.schooltimetable;

import com.solvd.schooltimetable.domain.CalendarDay;
import com.solvd.schooltimetable.domain.SchoolClass;
import com.solvd.schooltimetable.domain.Teacher;
import com.solvd.schooltimetable.genetic.GeneticAlgo;
import com.solvd.schooltimetable.genetic.GeneticAlgoConfig;
import com.solvd.schooltimetable.service.CalendarDayService;
import com.solvd.schooltimetable.service.SchoolClassService;
import com.solvd.schooltimetable.service.TeacherService;
import com.solvd.schooltimetable.service.impl.CalendarDayServiceImpl;
import com.solvd.schooltimetable.service.impl.SchoolClassServiceImpl;
import com.solvd.schooltimetable.service.impl.TeacherServiceImpl;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;
import java.util.stream.IntStream;

public class Main {

    private static final Logger LOGGER = LogManager.getLogger(Main.class);

    public static void main(String[] args) {
        TeacherService teacherService = new TeacherServiceImpl();
        CalendarDayService calendarDayService = new CalendarDayServiceImpl();
        SchoolClassService schoolClassService = new SchoolClassServiceImpl();

        List<SchoolClass> schoolClasses = schoolClassService.findAll();
        List<Teacher> teachers = teacherService.findAll();
        List<CalendarDay> calendarDays = calendarDayService.findAll();

        GeneticAlgoConfig geneticAlgoConfig = new GeneticAlgoConfig("geneticAlgoConfig.properties");
        if ((geneticAlgoConfig.getMinLessons() + geneticAlgoConfig.getMinLessons()) / 2 > teachers.size()) {
            throw new RuntimeException("Not enough teachers for this number of classes.");
        }
        GeneticAlgo geneticAlgo = new GeneticAlgo(geneticAlgoConfig);
        geneticAlgo.setSchoolClasses(schoolClasses);
        geneticAlgo.setTeachers(teachers);
        geneticAlgo.setCalendarDays(calendarDays);

        long t = System.currentTimeMillis();
        LOGGER.info(IntStream.range(0, 50)
                .parallel().boxed()
                .map(i -> new GeneticAlgo(geneticAlgoConfig))
                .mapToDouble(i -> {
                    i.setSchoolClasses(schoolClasses);
                    i.setTeachers(teachers);
                    i.setCalendarDays(calendarDays);
                    i.run();
                    return i.getCurrentBestFitness();
                })
                .filter(Double::isFinite)
                .average().getAsDouble());
        LOGGER.info(System.currentTimeMillis() - t);
    }
}
