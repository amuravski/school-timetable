package com.solvd.schooltimetable.domain;

import java.util.List;
import java.util.Objects;

public class ClassTimetable {

    private Long id;
    private SchoolClass schoolClass;
    private List<SchoolDay> schoolDays;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public SchoolClass getSchoolClass() {
        return schoolClass;
    }

    public void setSchoolClass(SchoolClass schoolClass) {
        this.schoolClass = schoolClass;
    }

    public List<SchoolDay> getSchoolDays() {
        return schoolDays;
    }

    public void setSchoolDays(List<SchoolDay> schoolDays) {
        this.schoolDays = schoolDays;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ClassTimetable)) return false;
        ClassTimetable that = (ClassTimetable) o;
        return Objects.equals(getId(), that.getId()) && Objects.equals(getSchoolClass(), that.getSchoolClass()) && Objects.equals(getSchoolDays(), that.getSchoolDays());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getSchoolClass(), getSchoolDays());
    }

    @Override
    public String toString() {
        return "ClassTimetable{" +
                "id=" + id +
                ", schoolClass=" + schoolClass +
                ", schoolDays=" + schoolDays +
                '}';
    }
}
