package com.solvd.schooltimetable.domain;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

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
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof SchoolTimetable)) {
            return false;
        }
        SchoolTimetable that = (SchoolTimetable) o;
        return hashcode == that.hashcode &&
                Objects.equals(id, that.id) &&
                Objects.equals(classTimetables, that.classTimetables);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, hashcode, classTimetables);
    }

    @Override
    public String toString() {

        //final int size = classTimetables.size();

        /////////////////////////////////////////////////////////////////
        final int tableNum = 8; // столбцы

        final int countDelimeter = 3; // строки

        //final int countDelimeter = numbLength(tableNum * tableNum);
        StringBuilder delimiterLine = createDelimiterLine(tableNum, countDelimeter);

        final StringBuilder sb = new StringBuilder();
        for (int i = 0; i < countDelimeter; i++)
            sb.append(createLine(tableNum, i, countDelimeter)).append("\r\n").append(delimiterLine).append("\r\n");
        System.out.println(sb);

        //classTimetables.stream().map().collect().forEach();

        return "SchoolTimetable{" +
                "id=" + id +
                ", hashcode=" + hashcode +
                ", classTimetables=" + classTimetables +
                '}';
    }

    /*private static String createLine(final int tableNum, final int multiplier, final int countDelimeter) {
        final int x = multiplier == 0 ? 1 : multiplier;
        return IntStream.range(1, tableNum).boxed()
                .map(i -> createNumb(i * x, countDelimeter, ""))
                .collect(Collectors.joining("|", createNumb(x,countDelimeter,"|"), "|"));
    }*/

    private static String createLine(final int tableNum, final int multiplier, final int countDelimeter) {
        //final int x = multiplier == 0 ? 1 : multiplier;
        return IntStream.range(1, tableNum).boxed()
                .map(i -> createNumb("15", countDelimeter, ""))
                .collect(Collectors.joining("|", createNumb("lessons", countDelimeter,"|"), "|"));
    }


    private static StringBuilder createDelimiterLine(final int tableNum, final int countDelimeter){
        final StringBuilder sb = new StringBuilder();
        for (int i = 0; i < tableNum; i++) {
            for (int j = 0; j < countDelimeter; j++) sb.append("-");
            sb.append("+");
        }
        return sb;
    }

    private static String createNumb (final String numb, final int countOfSpace, final String delemiter){
        final StringBuilder sb = new StringBuilder();
        /*for (int i = 0; i < countOfSpace - numbLength(numb); i++) */sb.append(" ");
        sb.append(numb).append(delemiter);
        return sb.toString();
    }

    private static int numbLength (int number){
        return (int) (Math.log10(number) + 1);
    }
}
