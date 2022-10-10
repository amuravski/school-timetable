package com.solvd.schooltimetable;

import com.solvd.schooltimetable.domain.CalendarDay;
import com.solvd.schooltimetable.domain.SchoolClass;
import com.solvd.schooltimetable.domain.SchoolTimetable;
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
import java.util.Optional;
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
        if (geneticAlgoConfig.getMinLessons() * geneticAlgoConfig.getMinWorkDays() < geneticAlgo.getNumberOfSubjectsWithTeachers()) {
            throw new RuntimeException("Not enough lessons in a week to include all subjects.");
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
        int n = 128;
        long t = System.currentTimeMillis();
        schoolClasses.remove(0);
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
                        .filter(thread -> thread.isGood(true))
                        .map(GeneticAlgo::getCurrentBest)
                        .findAny();
        LOGGER.info(generated
                .orElseThrow(() -> new RuntimeException("Unable to generate timetable with such arguments.")));
        LOGGER.info(System.currentTimeMillis() - t);
    }
}
