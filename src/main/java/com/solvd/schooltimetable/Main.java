package com.solvd.schooltimetable;

import com.solvd.schooltimetable.domain.CalendarDay;
import com.solvd.schooltimetable.domain.SchoolClass;
import com.solvd.schooltimetable.domain.Subject;
import com.solvd.schooltimetable.service.CalendarDayService;
import com.solvd.schooltimetable.service.SchoolClassService;
import com.solvd.schooltimetable.service.SubjectService;
import com.solvd.schooltimetable.service.impl.CalendarDayServiceImpl;
import com.solvd.schooltimetable.service.impl.SchoolClassServiceImpl;
import com.solvd.schooltimetable.service.impl.SubjectServiceImpl;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Main {

    private static final Logger LOGGER = LogManager.getLogger(Main.class);

    public static void main(String[] args) {
        CalendarDay calendarDay = new CalendarDay();
        calendarDay.setName("Paytnica");

        CalendarDayService calendarDayService = new CalendarDayServiceImpl();
        calendarDayService.create(calendarDay);
        LOGGER.info(calendarDayService.findById(calendarDay.getId()));

        calendarDay.setName("Sybbota");
        calendarDayService.update(calendarDay);
        LOGGER.info(calendarDayService.findById(calendarDay.getId()));
        calendarDayService.delete(calendarDay);

        Subject subject = new Subject();
        subject.setName("Istoriya");

        SubjectService subjectService = new SubjectServiceImpl();
        subjectService.create(subject);
        LOGGER.info(subjectService.findById(subject.getId()));

        subject.setName("Sybbota");
        subjectService.update(subject);
        LOGGER.info(subjectService.findById(subject.getId()));
        subjectService.delete(subject);

        SchoolClass schoolClass = new SchoolClass();
        schoolClass.setName("4A");

        SchoolClassService schoolClassService = new SchoolClassServiceImpl();
        schoolClassService.create(schoolClass);
        LOGGER.info(schoolClassService.findById(schoolClass.getId()));

        schoolClass.setName("4B");
        schoolClassService.update(schoolClass);
        LOGGER.info(schoolClassService.findById(schoolClass.getId()));
        schoolClassService.delete(schoolClass);
    }
}
