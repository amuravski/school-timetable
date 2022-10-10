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

        GeneticAlgoConfig geneticAlgoConfig = new GeneticAlgoConfig("geneticAlgoConfig.properties");
        GeneticAlgo geneticAlgo = new GeneticAlgo(geneticAlgoConfig);
        geneticAlgo.setSchoolClasses(schoolClasses);
        geneticAlgo.setTeachers(teachers);
        geneticAlgo.setCalendarDays(calendarDays);
        if (geneticAlgoConfig.getMinWorkDays() <= 0) {
            throw new RuntimeException("No working days on this week.");
        }
        if (geneticAlgoConfig.getMinWorkDays() > 7) {
            throw new RuntimeException("Working days more then 7 in a week.");
        }
        if (geneticAlgoConfig.getMinLessons() > geneticAlgoConfig.getMaxLessons()) {
            throw new RuntimeException("Min quantity of lessons more then max quantity of lessons.");
        }
        if (geneticAlgoConfig.getMinLessons() < 1) {
            throw new RuntimeException("Min quantity of lessons less 1.");
        }
        if (geneticAlgoConfig.getMaxLessons() > 9) {
            throw new RuntimeException("Max quantity of lessons more 9.");
        }
        if (geneticAlgoConfig.getMaxLessons() * geneticAlgoConfig.getMinWorkDays() < geneticAlgo.getNumberOfSubjectsWithTeachers()) {
            throw new RuntimeException("Not enough lessons in week to include all subjects.");
        }
        if (geneticAlgoConfig.getMinLessons() * schoolClasses.size() > teachers.size()) {
            throw new RuntimeException("Not enough teachers for this number of classes.");
        }
        if (geneticAlgoConfig.getElitismPercentileThreshold() < 1) {
            throw new RuntimeException("Not enough ElitismPercentileThreshold.");
        }
        if (geneticAlgoConfig.getGenerationPercentileThreshold() < 0) {
            throw new RuntimeException("Not enough GenerationPercentileThreshold.");
        }
        if (geneticAlgoConfig.getLuckyPercentileThreshold() < 1) {
            throw new RuntimeException("Not enough LuckyPercentileThreshold.");
        }
        if (geneticAlgoConfig.getMutationChance() < 0) {
            throw new RuntimeException("Not enough MutationChance.");
        }
        if (geneticAlgoConfig.getElitismPercentileThreshold() + geneticAlgoConfig.getGenerationPercentileThreshold() +
                geneticAlgoConfig.getLuckyPercentileThreshold() > 99) {
            throw new RuntimeException("Common percentiles = or more then 100.");
        }
        if (geneticAlgoConfig.getMutationChance() > 100) {
            throw new RuntimeException("MutationChance more then 100.");
        }
        if (geneticAlgoConfig.getPopulationSize() < 100) {
            throw new RuntimeException("Not enough PopulationSize.");
        }
        if (geneticAlgoConfig.getMaxIterations() < 50 * (int) Math.pow(schoolClasses.size(), 3)) {
            int maxIteration = 50 * (int) Math.pow(schoolClasses.size(), 3);
            throw new RuntimeException("Not enough MaxIterations. Should be more then " + maxIteration);
        }
        int n = 100;
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
