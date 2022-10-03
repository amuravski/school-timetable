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
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Teacher)) return false;
        Teacher teacher = (Teacher) o;
        return Objects.equals(getId(), teacher.getId())
                && Objects.equals(getFullName(),
                teacher.getFullName())
                && Objects.equals(getSubject(), teacher.getSubject());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getFullName(), getSubject());
    }

    @Override
    public String toString() {
        return "Teacher{" +
                "id=" + id +
                ", fullName='" + fullName + '\'' +
                ", subject=" + subject +
                '}';
    }
}
