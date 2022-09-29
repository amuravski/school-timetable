package com.solvd.school.domain;

import java.util.List;

public class Day {

    private Long id;
    private DayName dayName;
    private List<Lesson> lessons;

    public String toString() {
        return ("Day: id: " + this.id + " dayName: " + this.dayName + " lessons: " + this.lessons);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public DayName getDayName() {
        return dayName;
    }

    public void setDayName(DayName dayName) {
        this.dayName = dayName;
    }

    public List<Lesson> getLessons() {
        return lessons;
    }

    public void setLessons(List<Lesson> lessons) {
        this.lessons = lessons;
    }
}