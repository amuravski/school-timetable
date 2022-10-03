package com.solvd.schooltimetable.domain;

import java.util.Objects;

public class Teacher {

    private Long id;
    private String fullName;
    private Subject subject;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public Subject getSubject() {
        return subject;
    }

    public void setSubject(Subject subject) {
        this.subject = subject;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (obj == null || obj.getClass() != this.getClass()) {
            return false;
        }

        Teacher guest = (Teacher) obj;
        return Objects.equals(id, guest.id)
                && (Objects.equals(fullName, guest.fullName)
                || (fullName != null && fullName.equals(guest.getFullName())))
                && (subject == guest.subject
                || (subject != null && subject.equals(guest.getSubject()))
        );
    }

    @Override
    public String toString() {
        return "SchoolTimetable{ "
                + "FullName = " + fullName
                + " ID = " + id
                + " Subject = " + subject
                + " }";
    }
}
