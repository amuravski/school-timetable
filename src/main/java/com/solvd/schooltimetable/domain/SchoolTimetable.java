package com.solvd.schooltimetable.domain;

import java.util.List;
import java.util.Objects;

public class SchoolTimetable {

    private Long id;
    private List<ClassTimetable> classTimetables;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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
        return getId().equals(that.getId()) && getClassTimetables().equals(that.getClassTimetables());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getClassTimetables());
    }

    @Override
    public String toString() {
        return "SchoolTimetable{" +
                "id=" + id +
                ", classTimetables=" + classTimetables +
                '}';
    }
}
