package com.solvd.schooltimetable;

import com.solvd.schooltimetable.domain.*;
import com.solvd.schooltimetable.persistence.GeneticAlgo;
import com.solvd.schooltimetable.persistence.GeneticAlgoConfig;
import com.solvd.schooltimetable.service.*;
import com.solvd.schooltimetable.service.impl.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Arrays;
import java.util.List;

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
        geneticAlgo.run();
    }
}
