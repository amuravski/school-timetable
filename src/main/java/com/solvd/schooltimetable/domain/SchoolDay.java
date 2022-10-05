package com.solvd.schooltimetable.domain;

import java.util.List;
import java.util.Objects;

public class SchoolDay {

    private Long id;
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
                Objects.equals(calendarDay, schoolDay.calendarDay) &&
                Objects.equals(lessons, schoolDay.lessons);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, calendarDay, lessons);
    }

    @Override
    public String toString() {
        return "SchoolDay{" +
                "id=" + id +
                ", calendarDay=" + calendarDay +
                ", lessons=" + lessons +
                '}';
    }
}
