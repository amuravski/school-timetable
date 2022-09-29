package com.solvd.school.domain;

public class Lesson {

    private Long id;
    private Integer lessonNumber;
    private Subject subject;

    public String toString() {
        return ("\nLesson: id: " + this.id + " lessonNumber: " + this.lessonNumber);
    }

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

    public Subject getSubject() {
        return subject;
    }

    public void setSubject(Subject subject) {
        this.subject = subject;
    }
}