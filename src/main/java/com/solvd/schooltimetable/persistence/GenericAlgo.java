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

    public static SchoolTimetable getRandomSchoolTimetable(Integer minLesson, Integer maxLesson, Integer minWorkday) {
        SchoolTimetable schoolTimetable = new SchoolTimetable();
        TeacherService teacherService = new TeacherServiceImpl();
        CalendarDayService calendarDayService = new CalendarDayServiceImpl();
        ClassTimetableService classTimetableService = new ClassTimetableServiceImpl();
        SubjectService subjectService = new SubjectServiceImpl();
        SchoolClassService schoolClassService = new SchoolClassServiceImpl();
        List<SchoolClass> schoolClasses = schoolClassService.findAll();
        List<Subject> subjects = subjectService.findAll();

        List<ClassTimetable> classTimetables = schoolClasses.stream()
                    .map(schoolClass -> {
                        List<SchoolDay> schoolDays = new ArrayList<>();
                        for (int i = 0; i < minWorkday; i++) {
                            SchoolDay schoolDay = new SchoolDay();
                            switch (i+1) {
                                case (2):
                                    schoolDay.setCalendarDay(calendarDayService.findById(2L));
                                    break;
                                case (3):
                                    schoolDay.setCalendarDay(calendarDayService.findById(3L));
                                    break;
                                case (4):
                                    schoolDay.setCalendarDay(calendarDayService.findById(4L));
                                    break;
                                case (5):
                                    schoolDay.setCalendarDay(calendarDayService.findById(5L));
                                    break;
                                case (6):
                                    schoolDay.setCalendarDay(calendarDayService.findById(6L));
                                    break;
                                case (7):
                                    schoolDay.setCalendarDay(calendarDayService.findById(7L));
                                    break;
                                default:
                                    schoolDay.setCalendarDay(calendarDayService.findById(1L));
                                    break;
                            }
                            int numberOfLesson = (int) (Math.random() * ((maxLesson - minLesson + 1) + minLesson));// generated also 0 and 1, so add the checking below
                            if(numberOfLesson <2) {
                                numberOfLesson = 2;
                            }
                            List<Lesson> lessons= new ArrayList<>();
                            for (int j = 0; j < numberOfLesson; j++) {
                                Random rand = new Random();
                                Subject randomSubject = subjects.get(rand.nextInt(subjects.size()));
                                List<Teacher> teachers = teacherService.findBySubjectId(randomSubject.getId());
                                Teacher randomTeacher = teachers.get(rand.nextInt(teachers.size()));
                                Lesson lesson = new Lesson();
                                lesson.setLessonNumber(j+1);
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
                    })
                    .collect(Collectors.toList());
        schoolTimetable.setClassTimetables(classTimetables);
        return schoolTimetable;
    }
}
