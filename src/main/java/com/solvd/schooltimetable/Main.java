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

        int n = 16;
        long t = System.currentTimeMillis();

        schoolClasses.add(new SchoolClass("test1"));
        schoolClasses.add(new SchoolClass("test2"));
        LOGGER.info("Number of classes: " + schoolClasses.size());
        AtomicInteger c = new AtomicInteger(0);
        AtomicInteger g = new AtomicInteger(0);
        checkArguments(geneticAlgo);
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
                            c.incrementAndGet();
                            return geneticAlgoThread;
                        })
                        .filter(thread -> thread.isGood(true))
                        .map(GeneticAlgo::getCurrentBest)
                        //.forEach(i->g.incrementAndGet());
                        .findAny();
        //LOGGER.info("Generated: " + generated.isPresent());
        LOGGER.info("Threads completed: " + c);
        //LOGGER.info("Goods: " + g);
        LOGGER.info("Elapsed time: " + (System.currentTimeMillis() - t) / 1000.);
        //generated.orElseThrow(() -> new RuntimeException("Unable to generate timetable with such arguments."));
    }

    private static void checkArguments(GeneticAlgo geneticAlgo) {
        GeneticAlgoConfig geneticAlgoConfig = geneticAlgo.getGeneticAlgoConfig();
        int classNumber = geneticAlgo.getSchoolClasses().size();
        int teachersAmount = geneticAlgo.getTeachers().size();
        if (geneticAlgoConfig.getMinWorkDays() <= 0) {
            throw new RuntimeException("No working days in a week.");
        }
        if (geneticAlgoConfig.getMinWorkDays() > 7) {
            throw new RuntimeException("More than 7 days in a week.");
        }
        if (geneticAlgoConfig.getMinLessons() > geneticAlgoConfig.getMaxLessons()) {
            throw new RuntimeException("Min quantity of lessons bigger than max quantity of lessons.");
        }
        if (geneticAlgoConfig.getMinLessons() < 1) {
            throw new RuntimeException("Min quantity of lessons less than 1.");
        }
        if (geneticAlgoConfig.getMaxLessons() > 9) {
            throw new RuntimeException("Max quantity of lessons more than 9.");
        }
        if (geneticAlgoConfig.getMaxLessons() * geneticAlgoConfig.getMinWorkDays() < geneticAlgo.getNumberOfSubjectsWithTeachers()) {
            throw new RuntimeException("Not enough lessons in week to include all subjects.");
        }
        if (geneticAlgoConfig.getMinLessons() * geneticAlgoConfig.getMinWorkDays() < geneticAlgo.getNumberOfSubjectsWithTeachers()) {
            throw new RuntimeException("Not enough lessons in a week to include all subjects.");
        }
        if (geneticAlgoConfig.getMinLessons() * classNumber > teachersAmount) {
            throw new RuntimeException("Not enough teachers for this number of classes.");
        }
        if (geneticAlgoConfig.getElitismPercentileThreshold() < 1 && geneticAlgoConfig.isElitism()) {
            throw new RuntimeException("ElitismPercentileThreshold is too low.");
        }
        if (geneticAlgoConfig.getGenerationPercentileThreshold() < 1) {
            throw new RuntimeException("GenerationPercentileThreshold is too low.");
        }
        if (geneticAlgoConfig.getLuckyPercentileThreshold() < 1 && geneticAlgoConfig.isElitism()) {
            throw new RuntimeException("LuckyPercentileThreshold is too low.");
        }
        if (geneticAlgoConfig.getMutationChance() < 1) {
            throw new RuntimeException("MutationChance is too low.");
        }
        if (geneticAlgoConfig.getElitismPercentileThreshold() + geneticAlgoConfig.getGenerationPercentileThreshold() +
                geneticAlgoConfig.getLuckyPercentileThreshold() >= 100) {
            throw new RuntimeException("Common percentiles equal or more than 100.");
        }
        if (geneticAlgoConfig.getMutationChance() > 100) {
            throw new RuntimeException("MutationChance more than 100.");
        }
        if (geneticAlgoConfig.getPopulationSize() < 100) {
            geneticAlgoConfig.setPopulationSize(100);
            LOGGER.info("PopulationSize is too low. PopulationSize changed to: " + geneticAlgoConfig.getPopulationSize());
        }
        if (geneticAlgoConfig.getMaxIterations() < 100 * (int) Math.pow(classNumber, 2)) {
            geneticAlgoConfig.setMaxIterations(100 * (int) Math.pow(classNumber, 2));
            LOGGER.info("Not enough MaxIterations. MaxIterations changed to: " + geneticAlgoConfig.getMaxIterations());
        }
    }
}
