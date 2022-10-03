package com.solvd.schooltimetable.domain;

import java.util.Objects;

public class CalendarDay {

    private Long id;
    private String name;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CalendarDay)) return false;
        CalendarDay that = (CalendarDay) o;
        return Objects.equals(getId(), that.getId()) && Objects.equals(getName(), that.getName());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getName());
    }

    @Override
    public String toString() {
        return "CalendarDay{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
