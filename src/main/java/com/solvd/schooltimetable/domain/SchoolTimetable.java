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
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (obj == null || obj.getClass() != this.getClass()) {
            return false;
        }

        SchoolTimetable guest = (SchoolTimetable) obj;
        return Objects.equals(id, guest.id)
                && (Objects.equals(hashcode, guest.hashcode))
                && (classTimetables == guest.classTimetables
                || (classTimetables != null && classTimetables.equals(guest.getClassTimetables()))
        );
    }

    @Override
    public String toString() {
        return "SchoolTimetable{ "
                + "Hashcode = " + hashcode
                + " ID = " + id
                + " ClassTimetables = " + classTimetables
                + " }";
    }
}
