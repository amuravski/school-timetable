package com.solvd.schooltimetable;

import com.solvd.schooltimetable.domain.*;
import com.solvd.schooltimetable.service.*;
import com.solvd.schooltimetable.service.impl.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Arrays;

public class Main {

    private static final Logger LOGGER = LogManager.getLogger(Main.class);

    public static void main(String[] args) {

        TeacherService teacherService = new TeacherServiceImpl();
        SubjectService subjectService = new SubjectServiceImpl();
        LessonService lessonService = new LessonServiceImpl();
        SchoolDayService schoolDayService = new SchoolDayServiceImpl();
        CalendarDayService calendarDayService = new CalendarDayServiceImpl();
        ClassTimetableService classTimetableService = new ClassTimetableServiceImpl();

        Subject subject = new Subject();
        subject.setName("Subj1");
        subject.setId(2L);

        Teacher teacher = new Teacher();
        teacher.setSubject(subject);
        teacher.setFullName("TEST");

        // TEACHER

        teacherService.create(teacher);
//        LOGGER.info(teacherService.findById(teacher.getId()));

        System.out.println("\nteacherService.findAll() - CREATE\n");
        teacherService.findAll().forEach(t -> {
            System.out.println(t.getId() + " " + t.getFullName() + " " + t.getSubject().getName());
        });
        System.out.println("------\n");
//        System.exit(0);

        System.out.println("\nteacherService.findById()\n");
        teacher = teacherService.findById(teacher.getId());
        System.out.println(teacher.getId() + " " + teacher.getFullName() + " " + teacher.getSubject().getName());
        System.out.println("------\n");

        teacher.setFullName("UPDATED");
        teacher.setSubject(subject);
        teacherService.update(teacher);

        System.out.println("\nteacherService.findAll() - UPDATE\n");
        teacherService.findAll().forEach(t -> {
            System.out.println(t.getId() + " " + t.getFullName() + " " + t.getSubject().getName());
        });
        System.out.println("------\n");

        teacherService.deleteById(teacher.getId() - 1);
        teacherService.delete(teacher);

        System.out.println("\nteacherService.findAll() - DELETE\n");
        teacherService.findAll().forEach(t -> {
            System.out.println(t.getId() + " " + t.getFullName() + " " + t.getSubject().getName());
        });
        System.out.println("------\n");

        // SUBJECT

        subjectService.create(subject);
//        LOGGER.info(subjectService.findById(subject.getId()));

        System.out.println("\nsubjectService.findAll() - CREATE\n");
        subjectService.findAll().forEach(s -> {
            System.out.println(s.getId() + " " + s.getName());
        });
        System.out.println("------\n");

        System.out.println("\nsubjectService.findById()\n");
        subject = subjectService.findById(subject.getId());
        System.out.println(subject.getId() + " " + subject.getName());
        System.out.println("------\n");

        subject.setName("UPDATED");
        subjectService.update(subject);
//
        System.out.println("\nsubjectService.findAll() - UPDATE\n");
        subjectService.findAll().forEach(s -> {
            System.out.println(s.getId() + " " + s.getName());
        });
        System.out.println("------\n");

        subjectService.deleteById(subject.getId() - 1);
        subjectService.delete(subject);

        System.out.println("\nsubjectService.findAll() - DELETE\n");
        subjectService.findAll().forEach(s -> {
            System.out.println(s.getId() + " " + s.getName());
        });
        System.out.println("------\n");

        // LESSON

        Lesson lesson = new Lesson();
        lesson.setLessonNumber(1);
        lesson.setTeacher(teacherService.findById(2L));

        lessonService.create(lesson);

        System.out.println("\nlessonService.findAll() - CREATE\n");
        lessonService.findAll().forEach(l -> {
            System.out.println(l.getId() + " - " + l.getLessonNumber() + " - " + l.getTeacher().getFullName() + " - " + l.getTeacher().getSubject().getName());
        });
        System.out.println("------\n");

        System.out.println("\nlessonService.findById()\n");
        lesson = lessonService.findById(lesson.getId());
        System.out.println(lesson.getId() + " " + lesson.getLessonNumber() + " - " + lesson.getTeacher().getFullName() + " - " + lesson.getTeacher().getSubject().getName());
        System.out.println("------\n");

        lesson.setTeacher(teacherService.findById(4L));
        lessonService.update(lesson);

        System.out.println("\nlessonService.findAll() - UPDATE\n");
        lessonService.findAll().forEach(l -> {
            System.out.println(l.getId() + " " + l.getLessonNumber() + " - " + l.getTeacher().getFullName() + " - " + l.getTeacher().getSubject().getName());
        });
        System.out.println("------\n");

        lessonService.deleteById(lesson.getId() - 1);
        lessonService.delete(lesson);

        System.out.println("\nlessonService.findAll() - DELETE\n");
        lessonService.findAll().forEach(l -> {
            System.out.println(l.getId() + " " + l.getLessonNumber() + " - " + l.getTeacher().getFullName() + " - " + l.getTeacher().getSubject().getName());
        });
        System.out.println("------\n");

        // CalendarDay

        CalendarDay calendarDay = new CalendarDay();
        calendarDay.setName("day name new");

        System.out.println("\ncalendarDayService.findAll() - CREATE\n");
        calendarDayService.create(calendarDay);
        calendarDayService.findAll().forEach(cday -> {
            System.out.println("calendarDay=" + cday.getId() + " dayName=" + cday.getName());
        });
        System.out.println("------\n");

        System.out.println("\ncalendarDayService.findById()\n");
        calendarDay = calendarDayService.findById(calendarDay.getId());

        System.out.println("day name = " + calendarDay.getName());

        calendarDay.setName("UPDATE");
        calendarDayService.update(calendarDay);

        System.out.println("\ncalendarDayService.findAll() - UPDATE\n");
        calendarDayService.findAll().forEach(cday -> {
            System.out.println("calendarDay=" + cday.getId() + " dayName=" + cday.getName());
        });
        System.out.println("------\n");

        System.out.println("id to del " + calendarDay.getId());
//        calendarDayService.deleteById(4L);
        calendarDayService.delete(calendarDay);

        System.out.println("\ncalendarDayService.findAll() - DELETE\n");
        calendarDayService.findAll().forEach(cday -> {
            System.out.println("calendarDay=" + cday.getId() + " dayName=" + cday.getName());
        });
        System.out.println("------\n");

        // SchoolDay

        SchoolDay schoolDay = new SchoolDay();
        CalendarDay cd = calendarDayService.findById(1L);
        schoolDay.setCalendarDay(cd);
        schoolDay.setLessons(Arrays.asList(lesson, lesson));

        schoolDayService.create(schoolDay);

        System.out.println("\nschoolDayService.findAll() - schoolDayService.CREATE\n");
        schoolDayService.findAll().forEach(sd -> {
            System.out.println("schoolDay=" + sd.getId() + " getCalendarDay=" + sd.getCalendarDay());
            sd.getLessons().forEach(l -> {
                System.out.println(l.getLessonNumber() + " - " + l.getTeacher().getFullName() + " - " + l.getTeacher().getSubject().getName());
            });
        });
        System.out.println("------\n");

        System.out.println("\nschoolDayService.findById()\n");
        schoolDay = schoolDayService.findById(schoolDay.getId());
        System.out.println("schoolDay=" + schoolDay.getId() + " getCalendarDay=" + schoolDay.getCalendarDay());
        schoolDay.getLessons().forEach(l -> {
            System.out.println(l.getLessonNumber() + " - " + l.getTeacher().getFullName() + " - " + l.getTeacher().getSubject().getName());
        });

        cd.setName("cd4 UPDATE");
        schoolDay.setCalendarDay(cd);
        schoolDayService.update(schoolDay);

        schoolDayService.deleteById(schoolDay.getId());
//        schoolDayService.delete(schoolDay);

        System.out.println("\nschoolDayService.findAll() - DELETE\n");
        schoolDayService.findAll().forEach(sd -> {
            System.out.println("schoolDay=" + sd.getId() + " getCalendarDay=" + sd.getCalendarDay());
            sd.getLessons().forEach(l -> {
                System.out.println(l.getLessonNumber() + " - " + l.getTeacher().getFullName() + " - " + l.getTeacher().getSubject().getName());
            });
        });
        System.out.println("------\n");

        // ClassTimetable

//        ClassTimetable classTimetable = new ClassTimetable();
//        SchoolDay cd = calendarDayService.findById(1L);
//
//        classTimetable.setSchoolClass(cd);
//        classTimetable.setSchoolDays(cd);
//        schoolDayService.findAll(Arrays.asList(lesson, lesson));
//        schoolDayService.findAll();
//
////        classTimetableService.create(classTimetable);
//
//        System.out.println("\nclassTimetableService.findAll() - classTimetableService.CREATE\n");
//        classTimetableService.findAll().forEach(ct -> {
//            System.out.println("classTimetable=" + ct.getId() + " getCalendarDay=" + ct.getSchoolClass());
//            ct.getSchoolDays().forEach(d -> {
//                System.out.println(d.getCalendarDay());
//                d.getLessons().forEach(l -> System.out.println(l.getTeacher().getSubject().getName()));
//            });
//        });
//        System.out.println("------\n");

    }
}
