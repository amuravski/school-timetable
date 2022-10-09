package com.solvd.schooltimetable.domain;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

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
        List<Lesson> lessons;
        List<String> stringOut;
        int lessonsNumberInDay;
        stringOut = new ArrayList<>();
        int numberOfClasses = classTimetables.size();
        String line1 = "+-------------------------------------------------";
        String lineRepeat1 = new String(new char[numberOfClasses]).replace("\0", line1);
        String line2 = "+--------------------------+----------------------";
        String lineRepeat2 = new String(new char[numberOfClasses]).replace("\0", line2);
        String titleSubject = "| #   Subject              | Teacher              ";
        String titleSubjectRepeat = new String(new char[numberOfClasses]).replace("\0", titleSubject);

        List<CalendarDay> weekWorkDaysNames = classTimetables.stream()
                .flatMap(classTimetable -> classTimetable.getSchoolDays().stream())
                .map(schoolDay -> schoolDay.getCalendarDay())
                .distinct()
                .collect(Collectors.toList());

        stringOut.add(classTimetables.stream()
                .sorted((ct1, ct2) -> -ct2.getSchoolClass().getId().compareTo(ct1.getSchoolClass().getId()))
                .map(classTimetable ->
                        String.format("                       %4s class                 ", classTimetable.getSchoolClass().getName()))
                .collect(Collectors.joining()));
        stringOut.add(" \n" + lineRepeat1 + "+\n");

        for (Long j = 1L; j < weekWorkDaysNames.size() + 1; j++) {
            String strDay = String.format("Day %s - %-10s ", j, weekWorkDaysNames.get(Math.toIntExact(j - 1)).getName());
            String titleDay = "|                    " + strDay + "          ";
            String titleDayRepeat = new String(new char[numberOfClasses]).replace("\0", titleDay);
            stringOut.add(titleDayRepeat + "|\n");
            stringOut.add(lineRepeat2 + "|\n");
            Long finalJ = j;

            lessons = classTimetables.stream()
                    .sorted((ct1, ct2) -> -ct2.getSchoolClass().getId().compareTo(ct1.getSchoolClass().getId()))
                    .flatMap(classTimetable -> classTimetable.getSchoolDays().stream())
                    .filter(schoolDay -> (schoolDay.getCalendarDay().getId().equals(finalJ)))
                    .sorted((schoolDay1, schoolDay2) -> -schoolDay2.getId().compareTo(schoolDay1.getId()))
                    .flatMap(schoolDay -> schoolDay.getLessons().stream())
                    .collect(Collectors.toList());

            lessonsNumberInDay = (int) classTimetables.stream()
                    .flatMap(classTimetable -> classTimetable.getSchoolDays().stream())
                    .flatMap(schoolDay -> schoolDay.getLessons().stream())
                    .map(lesson -> lesson.getLessonNumber())
                    .distinct()
                    .count();

            stringOut.add(titleSubjectRepeat + "|\n");
            stringOut.add(lineRepeat2 + "+\n");
            for (int i = 1; i < lessonsNumberInDay + 1; i++) {
                int finalI = i;
                stringOut.add(lessons.stream()
                        .filter(lesson -> (lesson.getLessonNumber().equals(finalI)))
                        .map(lesson -> String.format("| %-1s   %-20s | %-20s ", lesson.getLessonNumber(), lesson.getTeacher().getSubject().getName(), lesson.getTeacher().getFullName()))
                        .collect(Collectors.joining()));
                stringOut.add("|\n");
            }
            stringOut.add(lineRepeat2 + "+\n");
        }
        String resultString = String.join("", stringOut);
        return resultString;
    }
}
