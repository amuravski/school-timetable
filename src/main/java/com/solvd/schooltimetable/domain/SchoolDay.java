package com.solvd.schooltimetable.domain;

import java.lang.reflect.Field;
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

    public String table(){

        final int tableNum = 2; // столбцы
        final int countDelimeter = 4; // строки



        /*lessons.get().getTeacher().getFullName();
        lessons.get().getTeacher().getSubject();*/

        StringBuilder delimiterLine = createDelimiterLine(tableNum, countDelimeter);

        final StringBuilder sb = new StringBuilder();

        Field field1 = lessons.get().getClass().getField();

        sb.append()lessons.get().getLessonNumber();

        for (int i = 0; i < countDelimeter; i++)
            //sb.append(delimeterColumn).append(метод вызова строки).append("\r\n").append(delimiterLine).append("\r\n")
            //sb.append(createLine(tableNum, i, countDelimeter)).append("\r\n").append(delimiterLine).append("\r\n");
        System.out.println(sb);

        return null;
    }

    private static String delimeterColumn(String str) {
        final StringBuilder sb = new StringBuilder();
        sb.append("|").append(str).append("|");
        return sb.toString();
    }

    private static String createLine(final int tableNum, String str, final int countDelimeter) {

        //lessons.stream().map(i -> createNumb(str, countDelimeter, "")).collect(Collectors.joining()).forEach(1, tableNum);

        return IntStream.range(1, tableNum).boxed()
                .map(i -> createNumb(15, countDelimeter, ""))
                .collect(Collectors.joining("|", createNumb(150,countDelimeter,"|"), "|"));
    }

    private static StringBuilder createDelimiterLine(final int tableNum, final int countDelimeter){
        final StringBuilder sb = new StringBuilder();
        for (int i = 0; i < tableNum; i++) {
            for (int j = 0; j < countDelimeter; j++) sb.append("-");
            sb.append("+");
        }
        return sb;
    }

    private static String createNumb (final int numb, final int countOfSpace, final String delemiter){
        final StringBuilder sb = new StringBuilder();
        sb.append(" ");
        sb.append(numb).append(delemiter);
        return sb.toString();
    }
}
