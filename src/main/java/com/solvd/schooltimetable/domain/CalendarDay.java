package com.solvd.schooltimetable.domain;

public class CalendarDay {

    private Long id;
    private String name;

    public CalendarDay() {
    }

    public CalendarDay(String name) {
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
