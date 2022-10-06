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
        SubjectService subjectService = new SubjectServiceImpl();
        LessonService lessonService = new LessonServiceImpl();
        SchoolDayService schoolDayService = new SchoolDayServiceImpl();
        CalendarDayService calendarDayService = new CalendarDayServiceImpl();
        ClassTimetableService classTimetableService = new ClassTimetableServiceImpl();
        SchoolTimetableService schoolTimetableService = new SchoolTimetableServiceImpl();
        SchoolClassService schoolClassService = new SchoolClassServiceImpl();

//        CalendarDay calendarDay = new CalendarDay("TEST_CALENDAR_DAY");
//        calendarDayService.create(calendarDay);
//        LOGGER.info(calendarDayService.findById(calendarDay.getId()));
//        calendarDay.setName("UPDATE_CALENDAR_DAY");
//        calendarDayService.update(calendarDay);
//        LOGGER.info(calendarDayService.findById(calendarDay.getId()));
//        calendarDayService.delete(calendarDay);
//
//        Subject subject = new Subject("TEST_SUBJECT");
//        subjectService.create(subject);
//        LOGGER.info(subjectService.findById(subject.getId()));
//        subject.setName("UPDATE_SUBJECT");
//        subjectService.update(subject);
//        LOGGER.info(subjectService.findById(subject.getId()));
//        subjectService.delete(subject);
//
//        SchoolClass schoolClass = new SchoolClass("TEST_CLASS");
//        schoolClassService.create(schoolClass);
//        LOGGER.info(schoolClassService.findById(schoolClass.getId()));
//        schoolClass.setName("UPD_CLASS");
//        schoolClassService.update(schoolClass);
//        LOGGER.info(schoolClassService.findById(schoolClass.getId()));
//        schoolClassService.delete(schoolClass);
//
//        Teacher teacher = new Teacher();
//        teacher.setSubject(subjectService.findById(1L));
//        teacher.setFullName("TEST");
//        teacherService.create(teacher);
//        LOGGER.info(teacherService.findById(teacher.getId()));
//        teacher.setFullName("UPDATED");
//        teacher.setSubject(subjectService.findById(2L));
//        teacherService.update(teacher);
//        LOGGER.info(teacherService.findById(teacher.getId()));
//        teacherService.delete(teacher);
//
//        Lesson lesson = new Lesson();
//        lesson.setSchoolDayId(1L);
//        lesson.setLessonNumber(1);
//        lesson.setTeacher(teacherService.findById(1L));
//        lessonService.create(lesson);
//        LOGGER.info("\nLESSON CREATED\n" + lessonService.findById(lesson.getId()));
//        lesson.setSchoolDayId(2L);
//        lesson.setLessonNumber(2);
//        lesson.setTeacher(teacherService.findById(2L));
//        lessonService.update(lesson);
//        LOGGER.info("\nLESSON UPDATED\n" + lessonService.findById(lesson.getId()));
//        lessonService.delete(lesson);
//
//        SchoolDay schoolDay = new SchoolDay();
//        schoolDay.setClassTimetableId(1L);
//        schoolDay.setCalendarDay(calendarDayService.findById(1L));
//        Lesson l1 = lessonService.findById(1L);
//        Lesson l2 = lessonService.findById(2L);
//        List<Lesson> lessons = (Arrays.asList(l1, l2));
//        schoolDay.setLessons(lessons);
//        schoolDayService.create(schoolDay);
//        LOGGER.info("\nSCHOOLDAY CREATED\n" + schoolDayService.findById(schoolDay.getId()));
//        schoolDay.setClassTimetableId(2L);
//        schoolDay.setCalendarDay(calendarDayService.findById(2L));
//        l1 = lessonService.findById(3L);
//        l2 = lessonService.findById(4L);
//        lessons = (Arrays.asList(l1, l2));
//        schoolDay.setLessons(lessons);
//        schoolDayService.update(schoolDay);
//        LOGGER.info("\nSCHOOLDAY UPDATED\n" + schoolDayService.findById(schoolDay.getId()));
//        schoolDayService.delete(schoolDay);
//
//        ClassTimetable classTimetable = new ClassTimetable();
//        classTimetable.setSchoolTimetableId(1L);
//        classTimetable.setSchoolClass(schoolClassService.findById(1L));
//        SchoolDay sd1 = schoolDayService.findById(1L);
//        SchoolDay sd2 = schoolDayService.findById(2L);
//        List<SchoolDay> schoolDays = (Arrays.asList(sd1, sd2));
//        classTimetable.setSchoolDays(schoolDays);
//        classTimetableService.create(classTimetable);
//        LOGGER.info("\nCLASSTIMETABLE CREATED\n" + classTimetableService.findById(classTimetable.getId()));
//        classTimetable.setSchoolTimetableId(2L);
//        classTimetable.setSchoolClass(schoolClassService.findById(1L));
//        sd1 = schoolDayService.findById(3L);
//        sd2 = schoolDayService.findById(4L);
//        schoolDays = (Arrays.asList(sd1, sd2));
//        classTimetable.setSchoolDays(schoolDays);
//        classTimetableService.update(classTimetable);
//        LOGGER.info("\nCLASSTIMETABLE UPDATED\n" + classTimetableService.findById(classTimetable.getId()));
//        classTimetableService.delete(classTimetable);
//
//        SchoolTimetable schoolTimetable = new SchoolTimetable();
//        schoolTimetable.setHashcode(1);
//        ClassTimetable ct1 = classTimetableService.findById(1L);
//        ClassTimetable ct2 = classTimetableService.findById(2L);
//        List<ClassTimetable> classTimetables = (Arrays.asList(ct1, ct2));
//        schoolTimetable.setClassTimetables(classTimetables);
//        schoolTimetableService.create(schoolTimetable);
//        LOGGER.info("\nSCHOOLTIMETABLE CREATED\n" + schoolTimetableService.findById(schoolTimetable.getId()));
//        schoolTimetable.setHashcode(2);
//        ct1 = classTimetableService.findById(3L);
//        ct2 = classTimetableService.findById(4L);
//        classTimetables = (Arrays.asList(ct1, ct2));
//        schoolTimetable.setClassTimetables(classTimetables);
//        schoolTimetableService.update(schoolTimetable);
//        LOGGER.info("\nSchoolTimetable UPDATED\n" + schoolTimetableService.findById(schoolTimetable.getId()));
//        schoolTimetableService.delete(schoolTimetable);

        GeneticAlgoConfig geneticAlgoConfig = new GeneticAlgoConfig("geneticAlgoConfig.properties");
        GeneticAlgo geneticAlgo = new GeneticAlgo(geneticAlgoConfig);
        List<SchoolClass> schoolClasses = schoolClassService.findAll();
        List<Teacher> teachers = teacherService.findAll();
        List<CalendarDay> calendarDays = calendarDayService.findAll();
        geneticAlgo.setSchoolClasses(schoolClasses);
        geneticAlgo.setTeachers(teachers);
        geneticAlgo.setCalendarDays(calendarDays);
        geneticAlgo.getFitness(schoolTimetableService.findById(1L));
//        geneticAlgo.run();
    }
}
