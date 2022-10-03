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
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (obj == null || obj.getClass() != this.getClass()) {
            return false;
        }

        ClassTimetable guest = (ClassTimetable) obj;
        return Objects.equals(id, guest.id)
                && (Objects.equals(schoolClass, guest.schoolClass)
                || (schoolClass != null && schoolClass.equals(guest.getSchoolClass())))
                && (schoolDays == guest.schoolDays
                || (schoolDays != null && schoolDays.equals(guest.getSchoolDays()))
        );
    }

    @Override
    public String toString() {
        return "ClassTimetable{ "
                + "schoolClass = " + schoolClass
                + " ID = " + id
                + " }";
    }
}
