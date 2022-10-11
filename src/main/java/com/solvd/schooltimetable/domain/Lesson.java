package com.solvd.schooltimetable.domain;

import java.util.Objects;

public class Lesson {

    private Long id;
    private Long schoolDayId;
    private Integer lessonNumber;
    private Teacher teacher;

    public Lesson() {
    }

    public Lesson(Teacher teacher) {
        this.teacher = teacher;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getSchoolDayId() {
        return schoolDayId;
    }

    public void setSchoolDayId(Long schoolDayId) {
        this.schoolDayId = schoolDayId;
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
        if (this == o) {
            return true;
        }
        if (!(o instanceof Lesson)) {
            return false;
        }
        Lesson lesson = (Lesson) o;
        return Objects.equals(id, lesson.id) &&
                Objects.equals(schoolDayId, lesson.schoolDayId) &&
                Objects.equals(lessonNumber, lesson.lessonNumber) &&
                Objects.equals(teacher, lesson.teacher);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, schoolDayId, lessonNumber, teacher);
    }

    @Override
    public String toString() {
        return "\nLesson{" +
                "id=" + id +
                ", schoolDayId=" + schoolDayId +
                ", lessonNumber=" + lessonNumber +
                ", teacher=" + teacher +
                '}';
    }
}
