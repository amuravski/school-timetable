package com.solvd.schooltimetable.domain;

import java.util.List;
import java.util.Objects;

public class ClassTimetable {

    private Long id;
    private Long schoolTimetableId;
    private SchoolClass schoolClass;
    private List<SchoolDay> schoolDays;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getSchoolTimetableId() {
        return schoolTimetableId;
    }

    public void setSchoolTimetableId(Long schoolTimetableId) {
        this.schoolTimetableId = schoolTimetableId;
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
        if (this == o) {
            return true;
        }
        if (!(o instanceof ClassTimetable)) {
            return false;
        }
        ClassTimetable that = (ClassTimetable) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(schoolTimetableId, that.schoolTimetableId) &&
                Objects.equals(schoolClass, that.schoolClass) &&
                Objects.equals(schoolDays, that.schoolDays);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, schoolTimetableId, schoolClass, schoolDays);
    }

    @Override
    public String toString() {
        return "ClassTimetable{" +
                "id=" + id +
                ", schoolTimetableId=" + schoolTimetableId +
                ", schoolClass=" + schoolClass +
                ", \nschoolDays=" + schoolDays +
                '}';
    }
}
