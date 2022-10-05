package com.solvd.schooltimetable.persistence;

import com.solvd.schooltimetable.domain.*;
import com.solvd.schooltimetable.service.*;
import com.solvd.schooltimetable.service.impl.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

public class GenericAlgo {

    private static final Logger LOGGER = LogManager.getLogger(GenericAlgo.class);
    private static GeneticAlgoConfig geneticAlgoConfig = new GeneticAlgoConfig("geneticAlgoConfig.properties");

    public GenericAlgo(GeneticAlgoConfig geneticAlgoConfig) {
        GenericAlgo.geneticAlgoConfig = geneticAlgoConfig;
    }

    public static SchoolTimetable getRandomSchoolTimetable() {
        Random rand = new Random();
        SchoolTimetable schoolTimetable = new SchoolTimetable();
        TeacherService teacherService = new TeacherServiceImpl();
        CalendarDayService calendarDayService = new CalendarDayServiceImpl();
        ClassTimetableService classTimetableService = new ClassTimetableServiceImpl();
        SubjectService subjectService = new SubjectServiceImpl();
        SchoolClassService schoolClassService = new SchoolClassServiceImpl();
        List<SchoolClass> schoolClasses = schoolClassService.findAll();
        List<Subject> subjects = subjectService.findAll();
        List<Teacher> teachers = teacherService.findAll();
        List<CalendarDay> calendarDays = calendarDayService.findAll();

        List<ClassTimetable> classTimetables = schoolClasses.stream().map(schoolClass -> {
            List<SchoolDay> schoolDays = new ArrayList<>();
            for (int i = 0; i < geneticAlgoConfig.getMinWorkDays(); i++) {
                SchoolDay schoolDay = new SchoolDay();
                schoolDay.setCalendarDay(calendarDays.get(i));
                int numberOfLesson = (int) (geneticAlgoConfig.getMinLessons() + Math.random() * (geneticAlgoConfig.getMaxLessons() - geneticAlgoConfig.getMinLessons() + 1));
                List<Lesson> lessons = new ArrayList<>();
                for (int j = 1; j < numberOfLesson + 1; j++) {
                    Teacher randomTeacher = teachers.get(rand.nextInt(teachers.size()));
                    Lesson lesson = new Lesson();
                    lesson.setLessonNumber(j);
                    lesson.setTeacher(randomTeacher);
                    lessons.add(lesson);
                }
                schoolDay.setLessons(lessons);
                schoolDays.add(schoolDay);
            }
            ClassTimetable classTimetable = new ClassTimetable();
            classTimetable.setSchoolClass(schoolClass);
            classTimetable.setSchoolDays(schoolDays);
            return classTimetable;
        }).collect(Collectors.toList());
        schoolTimetable.setClassTimetables(classTimetables);
        return schoolTimetable;
    }
}
