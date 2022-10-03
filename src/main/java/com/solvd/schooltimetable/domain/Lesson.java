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
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (obj == null || obj.getClass() != this.getClass()) {
            return false;
        }

        Lesson guest = (Lesson) obj;
        return Objects.equals(id, guest.id)
                && (Objects.equals(lessonNumber, guest.lessonNumber)
                || (lessonNumber != null && lessonNumber.equals(guest.getLessonNumber())))
                && (teacher == guest.teacher
                || (teacher != null && teacher.equals(guest.getTeacher()))
        );
    }

    @Override
    public String toString() {
        return "ClassTimetable{ "
                + "lessonNumber = " + lessonNumber
                + " ID = " + id
                + " Teacher = " + teacher
                + " }";
    }
}
