package com.solvd.schooltimetable.domain;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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
        return "SchoolTimetable{" +
                "classTimetables=" + classTimetables +
                '}';
    }

    public String toTableString() {
        List<Lesson> lessons;
        List<String> stringOut;
        int lessonsNumberInDay;
        stringOut = new ArrayList<>();
        stringOut.add("\n");
        int numberOfClasses = classTimetables.size();
        String line1 = "+–––––––––––––––––––––––––––––––––––––––––––––––––––";
        String lineRepeat1 = new String(new char[numberOfClasses]).replace("\0", line1);
        String line2 = "+––––––––––––––––––––––––––––+––––––––––––––––––––––";
        String lineRepeat2 = new String(new char[numberOfClasses]).replace("\0", line2);
        String titleSubject = "| #   Subject                | Teacher              ";
        String titleSubjectRepeat = new String(new char[numberOfClasses]).replace("\0", titleSubject);

        List<CalendarDay> weekWorkDaysNames = classTimetables.stream()
                .flatMap(classTimetable -> classTimetable.getSchoolDays().stream())
                .map(SchoolDay::getCalendarDay)
                .distinct()
                .collect(Collectors.toList());
        Supplier<Stream<ClassTimetable>> sortedClassTimetablesSupplier = () -> classTimetables.stream()
                .sorted((ct1, ct2) -> -ct2.getSchoolClass().getId().compareTo(ct1.getSchoolClass().getId()));
        stringOut.add(sortedClassTimetablesSupplier.get()
                .map(classTimetable ->
                        String.format("                       %4s class                   ",
                                classTimetable.getSchoolClass().getName()))
                .collect(Collectors.joining()));
        stringOut.add(" \n" + lineRepeat1 + "+\n");

        for (long j = 1L; j < weekWorkDaysNames.size() + 1; j++) {
            String strDay = String.format("Day %s - %-12s ", j,
                    weekWorkDaysNames.get(Math.toIntExact(j - 1)).getName());
            String titleDay = "|                    " + strDay + "          ";
            String titleDayRepeat = new String(new char[numberOfClasses]).replace("\0", titleDay);
            stringOut.add(titleDayRepeat + "|\n");
            stringOut.add(lineRepeat2 + "|\n");
            Long finalJ = j;

            lessons = sortedClassTimetablesSupplier.get()
                    .flatMap(classTimetable -> classTimetable.getSchoolDays().stream())
                    .filter(schoolDay -> (schoolDay.getCalendarDay().getId().equals(finalJ)))
                    .flatMap(schoolDay -> schoolDay.getLessons().stream())
                    .collect(Collectors.toList());

            lessonsNumberInDay = (int) classTimetables.stream()
                    .flatMap(classTimetable -> classTimetable.getSchoolDays().stream())
                    .flatMap(schoolDay -> schoolDay.getLessons().stream())
                    .map(Lesson::getLessonNumber)
                    .distinct()
                    .count();

            stringOut.add(titleSubjectRepeat + "|\n");
            stringOut.add(lineRepeat2 + "+\n");
            for (int i = 1; i < lessonsNumberInDay + 1; i++) {
                int finalI = i;
                stringOut.add(lessons.stream()
                        .filter(lesson -> (lesson.getLessonNumber().equals(finalI)))
                        .map(lesson -> String.format("| %-1s   %-22s | %-20s ", lesson.getLessonNumber(),
                                lesson.getTeacher().getSubject().getName(), lesson.getTeacher().getFullName()))
                        .collect(Collectors.joining()));
                stringOut.add("|\n");
            }
            stringOut.add(lineRepeat2 + "+\n");
        }
        return String.join("", stringOut);
    }
}
