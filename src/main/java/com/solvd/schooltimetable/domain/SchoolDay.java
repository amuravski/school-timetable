package com.solvd.schooltimetable.domain;

import java.lang.reflect.Field;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class SchoolDay {

    private Long id;
    private CalendarDay calendarDay;
    private List<Lesson> lessons;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public CalendarDay getCalendarDay() {
        return calendarDay;
    }

    public void setCalendarDay(CalendarDay calendarDay) {
        this.calendarDay = calendarDay;
    }

    public List<Lesson> getLessons() {
        return lessons;
    }

    public void setLessons(List<Lesson> lessons) {
        this.lessons = lessons;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof SchoolDay)) {
            return false;
        }
        SchoolDay schoolDay = (SchoolDay) o;
        return Objects.equals(id, schoolDay.id) &&
                Objects.equals(calendarDay, schoolDay.calendarDay) &&
                Objects.equals(lessons, schoolDay.lessons);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, calendarDay, lessons);
    }

    @Override
    public String toString() {
        return "SchoolDay{" +
                "id=" + id +
                ", calendarDay=" + calendarDay +
                ", lessons=" + lessons +
                '}';
    }

    public String table() {

        Integer firstColumnSize = SchoolDay.firstColumn(lessons);
        Integer secondColumnSize = SchoolDay.secondColumn(lessons);

        //константы ширина общая таблицы(в символах)
        //константы ширина 1 колонки
        //константы ширина второй колонки

        //константы метод делиметр ап даун
        //константы метод делиметр мид лайн

        final StringBuilder sb = new StringBuilder();

        sb.append(createUpAndDownLine(firstColumnSize, secondColumnSize)).append("\r\n");
        for (Lesson lesson : lessons) {

            String teacherName = lesson.getTeacher().getFullName();
            String subjectName = lesson.getTeacher().getSubject().getName();
            String lessonNumber = String.valueOf(lesson.getLessonNumber());

            sb.append(" ").append(lessonNumber).append("  ").append(subjectName)
                    .append(stringMultiply(" ", (firstColumnSize - subjectName.length() - 4)))
                    .append("|").append("\r\n");//первая колонка

            sb.append(" ").append(teacherName)
                    .append(stringMultiply(" ", (secondColumnSize - teacherName.length())))
                    .append("|").append("\r\n");

            sb.append(createDelimiterLine(firstColumnSize, secondColumnSize)).append("\r\n");
        }
        sb.append(createUpAndDownLine(firstColumnSize, secondColumnSize));
        return null;
    }

    private static String createDelimiterLine(int firstColumnSize, int secondColumnSize) {

        StringBuilder sb = new StringBuilder();
        sb.append("+----");
        for (int i = 0; i <= firstColumnSize; i++) {
            sb.append("-");
        }
        sb.append("|");
        for (int i = 0; i <= secondColumnSize; i++) {
            sb.append("-");
        }
        sb.append("+");
        return sb.toString();
    }

    public static String stringMultiply(String s, int n) {//метод для получения строк пробелов
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < n; i++) {
            sb.append(s);
        }
        return sb.toString();
    }

    public static Integer firstColumn(List<Lesson> lessons) {

        int largestString = lessons.get(0).getTeacher().getSubject().getName().length();

        for (Lesson lesson : lessons) {
            if (lesson.getTeacher().getSubject().getName().length() > largestString) {
                largestString = lesson.getTeacher().getSubject().getName().length();
            }
        }
        return largestString;

    }

    public static Integer secondColumn(List<Lesson> lessons) {

        int largestString = lessons.get(0).getTeacher().getFullName().length();

        for (Lesson lesson : lessons) {
            if (lesson.getTeacher().getFullName().length() > largestString) {
                largestString = lesson.getTeacher().getFullName().length();
            }
        }

        return largestString;
    }

    public static String createUpAndDownLine(Integer firstColumnSize, Integer secondColumnSize) {

        StringBuilder sb = new StringBuilder();
        sb.append("+----");
        for (int i = 0; i <= firstColumnSize; i++) {
            sb.append("-");
        }
        sb.append("+");
        for (int i = 0; i <= secondColumnSize; i++) {
            sb.append("-");
        }
        sb.append("+");

        return sb.toString();
    }
}
