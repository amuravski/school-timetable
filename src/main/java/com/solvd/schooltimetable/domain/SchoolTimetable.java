package com.solvd.schooltimetable.domain;

import java.util.List;
import java.util.Objects;

public class SchoolTimetable {

    private Long id;
    private int hashcode;
    private List<ClassTimetable> classTimetables;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getHashcode() {
        return hashcode;
    }

    public void setHashcode(int hashcode) {
        this.hashcode = hashcode;
    }

    public List<ClassTimetable> getClassTimetables() {
        return classTimetables;
    }

    public void setClassTimetables(List<ClassTimetable> classTimetables) {
        this.classTimetables = classTimetables;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof SchoolTimetable)) return false;
        SchoolTimetable that = (SchoolTimetable) o;
        return getHashcode() == that.getHashcode() && Objects.equals(getId(), that.getId()) && Objects.equals(getClassTimetables(), that.getClassTimetables());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getHashcode(), getClassTimetables());
    }

    @Override
    public String toString() {
        return "SchoolTimetable{" +
                "id=" + id +
                ", hashcode=" + hashcode +
                ", classTimetables=" + classTimetables +
                '}';
    }
}
