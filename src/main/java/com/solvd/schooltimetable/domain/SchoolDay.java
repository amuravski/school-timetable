package com.solvd.schooltimetable.domain;

import com.solvd.schooltimetable.service.CalendarDayService;
import com.solvd.schooltimetable.service.impl.CalendarDayServiceImpl;

import java.util.List;
import java.util.Objects;

public class SchoolDay {

    CalendarDayService calendarDayService = new CalendarDayServiceImpl();

    private Long id;
    private Long classTimetableId;
    private CalendarDay calendarDay;
    private List<Lesson> lessons;

    public SchoolDay() {
    }

    public SchoolDay(CalendarDay calendarDay) {
        this.calendarDay = calendarDay;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getClassTimetableId() {
        return classTimetableId;
    }

    public void setClassTimetableId(Long classTimetableId) {
        this.classTimetableId = classTimetableId;
    }

    public CalendarDay getCalendarDay() {
        return calendarDay;
    }

    public void setCalendarDay(CalendarDay calendarDay) {
        this.calendarDay = calendarDay;
    }

    public List<Lesson> getLessons() {
        return lessons;
    }

    public void setLessons(List<Lesson> lessons) {
        this.lessons = lessons;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof SchoolDay)) {
            return false;
        }
        SchoolDay schoolDay = (SchoolDay) o;
        return Objects.equals(id, schoolDay.id) &&
                Objects.equals(classTimetableId, schoolDay.classTimetableId) &&
                Objects.equals(calendarDay, schoolDay.calendarDay) &&
                Objects.equals(lessons, schoolDay.lessons);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, classTimetableId, calendarDay, lessons);
    }

    @Override
    public String toString() {
        return "\nSchoolDay{" +
                "id=" + id +
                ", classTimetableId=" + classTimetableId + " " + calendarDayService.findById(classTimetableId).getName() +
                ", calendarDay=" + calendarDay +
                ", \nlessons=" + lessons +
                '}';
    }
}
