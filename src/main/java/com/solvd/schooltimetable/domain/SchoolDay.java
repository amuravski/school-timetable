package com.solvd.schooltimetable.domain;

import java.util.List;
import java.util.Objects;

public class SchoolDay {

    private Long id;
    private CalendarDay calendarDay;
    private List<Lesson> lessons;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((calendarDay == null) ? 0 : calendarDay.hashCode());
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        result = prime * result + ((lessons == null) ? 0 : lessons.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (obj == null || obj.getClass() != this.getClass()) {
            return false;
        }

        SchoolDay guest = (SchoolDay) obj;
        return Objects.equals(id, guest.id)
                && (Objects.equals(calendarDay, guest.calendarDay)
                || (calendarDay != null && calendarDay.equals(guest.getCalendarDay())))
                && (lessons == guest.lessons
                || (lessons != null && lessons.equals(guest.getLessons()))
        );
    }

    @Override
    public String toString() {
        return "SchoolDay{ "
                + "CalendarDay = " + calendarDay
                + " ID = " + id
                + " Lessons = " + lessons
                + " }";
    }
}
