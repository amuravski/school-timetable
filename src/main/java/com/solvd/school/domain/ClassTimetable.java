package com.solvd.school.domain;

import java.util.List;

public class ClassTimetable {

    private Long id;
    private ClassName className;
    private List<Day> days;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ClassName getSchoolClass() {
        return className;
    }

    public void setSchoolClass(ClassName className) {
        this.className = className;
    }

    public List<Day> getDays() {
        return days;
    }

    public void setDays(List<Day> days) {
        this.days = days;
    }
}