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
import java.util.concurrent.atomic.AtomicInteger;
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
        ///
        SchoolTimetableService schoolTimetableService = new SchoolTimetableServiceImpl();
        SchoolTimetable st1 = schoolTimetableService.findById(1L);
        System.out.println(st1);

        SchoolTimetable st2 = schoolTimetableService.findById(2L);
        System.out.println(st2);

        System.exit(0);
        ////
        GeneticAlgoConfig geneticAlgoConfig = new GeneticAlgoConfig("geneticAlgoConfig.properties");
        GeneticAlgo geneticAlgo = new GeneticAlgo(geneticAlgoConfig);
        geneticAlgo.setSchoolClasses(schoolClasses);
        geneticAlgo.setTeachers(teachers);
        geneticAlgo.setCalendarDays(calendarDays);
        if (geneticAlgoConfig.getMaxLessons() * geneticAlgoConfig.getMinWorkDays() < geneticAlgo.getNumberOfSubjectsWithTeachers()) {
            throw new RuntimeException("Not enough lessons in week to include all subjects.");
        }
        if (geneticAlgoConfig.getMinLessons() * schoolClasses.size() > teachers.size()) {
            throw new RuntimeException("Not enough teachers for this number of classes.");
        }
        int n = 5;
        long t = System.currentTimeMillis();
        AtomicInteger ideals = new AtomicInteger(0);
        LOGGER.info(IntStream.range(0, n).parallel().boxed()
                .map(i -> new GeneticAlgo(geneticAlgoConfig))
                .mapToDouble(i -> {
                    i.setSchoolClasses(schoolClasses);
                    i.setTeachers(teachers);
                    i.setCalendarDays(calendarDays);
                    i.run();
                    if (i.isGood()) {
                        ideals.incrementAndGet();
                    }
                    return i.getCurrentBestFitness();
                })
                .filter(Double::isFinite)
                .average().getAsDouble());
        LOGGER.info("Ideals out of " + n + ": " + ideals.get());
        LOGGER.info(System.currentTimeMillis() - t);
    }
}
