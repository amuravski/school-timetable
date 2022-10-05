package com.solvd.schooltimetable;

import com.solvd.schooltimetable.domain.*;
import com.solvd.schooltimetable.persistence.GeneticAlgo;
import com.solvd.schooltimetable.persistence.GeneticAlgoConfig;
import com.solvd.schooltimetable.service.*;
import com.solvd.schooltimetable.service.impl.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.List;

public class Main {

    private static final Logger LOGGER = LogManager.getLogger(Main.class);

    public static void main(String[] args) {
        TeacherService teacherService = new TeacherServiceImpl();
        SubjectService subjectService = new SubjectServiceImpl();
        LessonService lessonService = new LessonServiceImpl();
        SchoolDayService schoolDayService = new SchoolDayServiceImpl();
        CalendarDayService calendarDayService = new CalendarDayServiceImpl();
        ClassTimetableService classTimetableService = new ClassTimetableServiceImpl();

        CalendarDay calendarDay = new CalendarDay("TEST_CALENDAR_DAY");
        calendarDayService.create(calendarDay);
        LOGGER.info(calendarDayService.findById(calendarDay.getId()));
        calendarDay.setName("UPDATE_CALENDAR_DAY");
        calendarDayService.update(calendarDay);
        LOGGER.info(calendarDayService.findById(calendarDay.getId()));
        calendarDayService.delete(calendarDay);

        Subject subject = new Subject("TEST_SUBJECT");
        subjectService.create(subject);
        LOGGER.info(subjectService.findById(subject.getId()));
        subject.setName("UPDATE_SUBJECT");
        subjectService.update(subject);
        LOGGER.info(subjectService.findById(subject.getId()));
        subjectService.delete(subject);

        SchoolClass schoolClass = new SchoolClass("TEST_CLASS");
        SchoolClassService schoolClassService = new SchoolClassServiceImpl();
        schoolClassService.create(schoolClass);
        LOGGER.info(schoolClassService.findById(schoolClass.getId()));
        schoolClass.setName("UPD_CLASS");
        schoolClassService.update(schoolClass);
        LOGGER.info(schoolClassService.findById(schoolClass.getId()));
        schoolClassService.delete(schoolClass);

        Teacher teacher = new Teacher();
        teacher.setSubject(subjectService.findById(1L));
        teacher.setFullName("TEST");
        teacherService.create(teacher);
        LOGGER.info(teacherService.findById(teacher.getId()));
        teacher.setFullName("UPDATED");
        teacher.setSubject(subjectService.findById(2L));
        teacherService.update(teacher);
        LOGGER.info(teacherService.findById(teacher.getId()));
        teacherService.delete(teacher);

        Lesson lesson = new Lesson();
        lesson.setLessonNumber(1);
        lesson.setTeacher(teacherService.findById(1L));
        lessonService.create(lesson);
        LOGGER.info(lessonService.findById(lesson.getId()));
        lesson.setLessonNumber(2);
        lesson.setTeacher(teacherService.findById(2L));
        lessonService.update(lesson);
        LOGGER.info(lessonService.findById(lesson.getId()));
        lessonService.delete(lesson);

        GeneticAlgoConfig geneticAlgoConfig = new GeneticAlgoConfig("geneticAlgoConfig.properties");
        GeneticAlgo geneticAlgo = new GeneticAlgo(geneticAlgoConfig);
        LOGGER.info(geneticAlgoConfig);
        SchoolTimetable schoolTimetable = geneticAlgo.getRandomSchoolTimetable();
        LOGGER.info(schoolTimetable);
        List<SchoolTimetable> schoolTimetables = geneticAlgo.getPopulation();
        List<SchoolTimetable> schoolTimetables1 = new ArrayList<>();
        schoolTimetables1.add(schoolTimetables.get(0));
        schoolTimetables1.add(schoolTimetables.get(1));
        schoolTimetables1.add(schoolTimetables.get(2));
        schoolTimetables1.add(schoolTimetables.get(3));
        schoolTimetables1.add(schoolTimetables.get(4));
        schoolTimetables1.add(schoolTimetables.get(5));
        List<SchoolTimetable> schoolTimetables2 = geneticAlgo.complementPopulation(schoolTimetables1);
        LOGGER.info(schoolTimetables.size());
        LOGGER.info(schoolTimetables2.size());
    }
}
