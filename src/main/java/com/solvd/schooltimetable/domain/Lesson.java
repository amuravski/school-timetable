package com.solvd.schooltimetable.domain;

import java.util.Objects;

public class Lesson {

    private Long id;
    private Integer lessonNumber;
    private Teacher teacher;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getLessonNumber() {
        return lessonNumber;
    }

    public void setLessonNumber(Integer lessonNumber) {
        this.lessonNumber = lessonNumber;
    }

    public Teacher getTeacher() {
        return teacher;
    }

    public void setTeacher(Teacher teacher) {
        this.teacher = teacher;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Lesson)) return false;
        Lesson lesson = (Lesson) o;
        return Objects.equals(getId(), lesson.getId()) && Objects.equals(getLessonNumber(), lesson.getLessonNumber()) && Objects.equals(getTeacher(), lesson.getTeacher());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getLessonNumber(), getTeacher());
    }

    @Override
    public String toString() {
        return "Lesson{" +
                "id=" + id +
                ", lessonNumber=" + lessonNumber +
                ", teacher=" + teacher +
                '}';
    }
}
