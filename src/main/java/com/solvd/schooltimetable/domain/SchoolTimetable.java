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
        if (this == o) {
            return true;
        }
        if (!(o instanceof SchoolTimetable)) {
            return false;
        }
        SchoolTimetable that = (SchoolTimetable) o;
        return hashcode == that.hashcode &&
                Objects.equals(id, that.id) &&
                Objects.equals(classTimetables, that.classTimetables);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, hashcode, classTimetables);
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
