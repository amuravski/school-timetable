package com.solvd.schooltimetable.persistence;

import com.solvd.schooltimetable.domain.*;
import com.solvd.schooltimetable.service.SchoolDayService;
import com.solvd.schooltimetable.service.impl.SchoolDayServiceImpl;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class GeneticAlgo {

    private final GeneticAlgoConfig geneticAlgoConfig;
    private List<SchoolClass> schoolClasses;
    private List<Teacher> teachers;
    private List<CalendarDay> calendarDays;
    private SchoolTimetable currentBest;

    public GeneticAlgo(GeneticAlgoConfig geneticAlgoConfig) {
        this.geneticAlgoConfig = geneticAlgoConfig;
    }

    public void run() {
        List<SchoolTimetable> population = getPopulation();
        for (int i = 0; i < geneticAlgoConfig.getMaxIterations(); i++) {
            if (getFitness(currentBest) == Double.POSITIVE_INFINITY) {
                return;
            }
            population = iterateGeneration(population);
        }
    }

    public List<SchoolTimetable> iterateGeneration(List<SchoolTimetable> population) {
        List<SchoolTimetable> rated = getRatedPopulation(population);
        currentBest = rated.get(0);
        List<SchoolTimetable> newPopulation = new ArrayList<>();
        if (geneticAlgoConfig.isElitism()) {
            newPopulation.addAll(rated.subList(0,
                    population.size() / 100 * geneticAlgoConfig.getElitismPercentileThreshold()));
        }
        List<SchoolTimetable> willCross = rated.stream()
                .filter(schoolTimetable -> getFitness(schoolTimetable) >= 0.)
                .limit(population.size() / 100 * geneticAlgoConfig.getGenerationPercentileThreshold())
                .collect(Collectors.toList());
        List<SchoolTimetable> shuffledWillCross = new ArrayList<>(willCross);
        Collections.shuffle(shuffledWillCross);
        int lastElementIndex = willCross.size() - 1;
        if (willCross.get(lastElementIndex).equals(shuffledWillCross.get(lastElementIndex))) {
            shuffledWillCross.add(0, shuffledWillCross.remove(lastElementIndex));
        }
        willCross.forEach(p1 -> {
            if (shuffledWillCross.size() > 0) {
                SchoolTimetable p2;
                if (!p1.equals(shuffledWillCross.get(0))) {
                    p2 = shuffledWillCross.remove(0);
                }
                else {
                    p2 = shuffledWillCross.remove(1);
                }
                SchoolTimetable offspring = getOffspring(p1, p2);
                newPopulation.add(offspring);
            }
        });
        complementPopulation(newPopulation);
        return newPopulation;
    }

    public List<SchoolTimetable> getRatedPopulation(List<SchoolTimetable> population) {
        return new ArrayList<>(sortMapByValue(population.stream()
                .collect(Collectors
                        .toMap(schoolTimetable -> schoolTimetable, this::getFitness, (u, v) -> u, HashMap::new)))
                .keySet());
    }

    public double getFitness(SchoolTimetable schoolTimetable) {

        SchoolDayService schoolDayService = new SchoolDayServiceImpl();

        List<Lesson> lessons = schoolTimetable.getClassTimetables().stream()
                .peek(classTimetable -> System.out.println("CLASS TT " + classTimetable.getSchoolClass().getName()))
                .flatMap(classTimetable -> classTimetable.getSchoolDays().stream())
                .peek(schoolDay -> System.out.println(" S DAY " + schoolDay.getCalendarDay().getName()))
                .flatMap(schoolDay -> schoolDay.getLessons().stream())
                .peek(lesson -> System.out.println("  LES " + lesson.getLessonNumber() + " " + lesson.getTeacher().getSubject().getName() + " " + lesson.getTeacher().getFullName()))
                .collect(Collectors.toList());

        lessons.stream()
                .filter(l -> l.getLessonNumber() != null)
                .filter(l -> l.getSchoolDayId() != null)
                .forEach(lesson1 -> {
                    System.out.println("TAKE LESSON TO COMPARE: id=" + lesson1.getSchoolDayId() +
                            ", number=" + lesson1.getLessonNumber() +
                            ", teacher_id=" + lesson1.getTeacher().getId() + ", teacher name=" + lesson1.getTeacher().getFullName());

                    lessons.stream()
                            .filter(l -> l.getLessonNumber() != null)
                            .filter(l -> l.getSchoolDayId() != null)
                            .filter(l -> l.getLessonNumber().equals(lesson1.getLessonNumber()))
                            .filter(l -> l.getTeacher().equals(lesson1.getTeacher()))
                            .filter(l -> schoolDayService.findById(l.getSchoolDayId()).getCalendarDay().getName()
                                    .equals(schoolDayService.findById(lesson1.getSchoolDayId()).getCalendarDay().getName()))
                            .filter(l -> !(l.getId().equals(lesson1.getId())))
                            .forEach(l -> System.out.println(
                                    "\ndays: " + schoolDayService.findById(l.getSchoolDayId()).getCalendarDay().getName() + " == " +
                                            schoolDayService.findById(lesson1.getSchoolDayId()).getCalendarDay().getName() +
                                            "\nlessons numers: " + l.getLessonNumber() + " == " + lesson1.getLessonNumber() +
                                            "\nlessons teachers: " + l.getTeacher() + " == " + lesson1.getTeacher() +
                                            " == " + schoolDayService.findById(lesson1.getSchoolDayId()).getCalendarDay().getName()
                            ));
                });

        schoolTimetable.getClassTimetables().stream()
                .peek(classTimetable -> System.out.println("\nCLASS TT " + classTimetable.getSchoolClass().getName()))
                .flatMap(classTimetable -> {

                            List<Lesson> ls = classTimetable.getSchoolDays().stream()
                                    .flatMap(schoolDay -> schoolDay.getLessons().stream())
                                    .collect(Collectors.toList());

                            List<Subject> subjectsAllOfClass = ls.stream().map(lesson -> lesson.getTeacher().getSubject())
                                    .collect(Collectors.toList());

                            List<Subject> subjectsDistOfClass =
                                    ls.stream().
                                            map(lesson -> lesson.getTeacher().getSubject())
                                            .distinct()
                                            .collect(Collectors.toList());

                            System.out.println("\n SUM of subjects of class" +
                                    subjectsDistOfClass.stream().
                                            map(subject -> {
                                                return
                                                        subjectsAllOfClass.stream()
                                                                .filter(subject1 -> subject1.equals(subject)).count();
                                            })
                                            .collect(Collectors.toList())
                            );
                            return null;
                        }
                ).count();
        return 0.;
    }

    public Double std(List<Integer> subjectCounts) {
        System.out.println("STD " + std(subjectCounts));
        return null;
    }

    public SchoolTimetable getOffspring(SchoolTimetable p1, SchoolTimetable p2) {
        SchoolTimetable offspring = new SchoolTimetable();
        offspring.setClassTimetables(new ArrayList<>());
        List<ClassTimetable> p1TimeTables = p1.getClassTimetables();
        List<ClassTimetable> p2TimeTables = p2.getClassTimetables();
        List<ClassTimetable> offspringTimetables = new ArrayList<>();
        for (int i = 0; i < p1TimeTables.size(); i++) {
            offspringTimetables.add(getOffspring(p1TimeTables.get(i), p2TimeTables.get(i)));
        }
        offspring.setClassTimetables(offspringTimetables);
        return offspring;
    }

    public SchoolTimetable getRandomSchoolTimetable() {
        SchoolTimetable result = new SchoolTimetable();
        List<ClassTimetable> classTimetables = schoolClasses.stream().map(schoolClass -> {
            Random rand = new Random();
            List<SchoolDay> schoolDays = new ArrayList<>();
            for (int i = 0; i < geneticAlgoConfig.getMinWorkDays(); i++) {
                SchoolDay schoolDay = new SchoolDay();
                schoolDay.setCalendarDay(calendarDays.get(i));
                int numberOfLessons = (int) (geneticAlgoConfig.getMinLessons() +
                        Math.random() *
                                (geneticAlgoConfig.getMaxLessons() - geneticAlgoConfig.getMinLessons() + 1));
                List<Lesson> lessons = new ArrayList<>();
                for (int j = 1; j < numberOfLessons + 1; j++) {
                    Teacher randomTeacher = teachers.get(rand.nextInt(teachers.size()));
                    Lesson lesson = new Lesson(randomTeacher);
                    lesson.setLessonNumber(j);
                    lessons.add(lesson);
                }
                schoolDay.setLessons(lessons);
                schoolDays.add(schoolDay);
            }
            ClassTimetable classTimetable = new ClassTimetable();
            classTimetable.setSchoolClass(schoolClass);
            classTimetable.setSchoolDays(schoolDays);
            return classTimetable;
        }).collect(Collectors.toList());
        result.setClassTimetables(classTimetables);
        return result;
    }

    public List<SchoolTimetable> getPopulation() {
        List<SchoolTimetable> schoolTimetables = new ArrayList<>();
        for (int i = 0; i < geneticAlgoConfig.getPopulationSize(); i++) {
            schoolTimetables.add(getRandomSchoolTimetable());
        }
        return schoolTimetables;
    }

    public void complementPopulation(List<SchoolTimetable> schoolTimetables) {
        int currentPopulationSize = schoolTimetables.size();
        int complementPopulationSize = geneticAlgoConfig.getPopulationSize() - currentPopulationSize;
        IntStream.range(0, complementPopulationSize)
                .forEach((i) -> schoolTimetables.add(getRandomSchoolTimetable()));
    }

    public List<SchoolClass> getSchoolClasses() {
        return schoolClasses;
    }

    public void setSchoolClasses(List<SchoolClass> schoolClasses) {
        this.schoolClasses = schoolClasses;
    }

    public List<Teacher> getTeachers() {
        return teachers;
    }

    public void setTeachers(List<Teacher> teachers) {
        this.teachers = teachers;
    }

    public List<CalendarDay> getCalendarDays() {
        return calendarDays;
    }

    public void setCalendarDays(List<CalendarDay> calendarDays) {
        this.calendarDays = calendarDays;
    }

    private ClassTimetable getOffspring(ClassTimetable p1, ClassTimetable p2) {
        ClassTimetable offspring = new ClassTimetable(p1.getSchoolClass());
        List<SchoolDay> p1Days = p1.getSchoolDays();
        List<SchoolDay> p2Days = p2.getSchoolDays();
        List<SchoolDay> offspringDays = new ArrayList<>();
        for (int i = 0; i < p1Days.size(); i++) {
            offspringDays.add(getOffspring(p1Days.get(i), p2Days.get(i)));
        }
        offspring.setSchoolDays(offspringDays);
        return offspring;
    }

    private SchoolDay getOffspring(SchoolDay p1, SchoolDay p2) {
        Random random = new Random();
        SchoolDay offspring = new SchoolDay(p1.getCalendarDay());
        List<Lesson> p1Lessons = p1.getLessons();
        List<Lesson> p2Lessons = p2.getLessons();
        List<Lesson> offspringLessons = new ArrayList<>();
        int i;
        int lessonNumber = 1;
        for (i = 0; i < Integer.min(p1Lessons.size(), p2Lessons.size()); i++) {
            Lesson lesson = new Lesson((random.nextBoolean() ? p1Lessons.get(i) : p2Lessons.get(i)).getTeacher());
            lesson.setLessonNumber(lessonNumber);
            offspringLessons.add(lesson);
            lessonNumber++;
        }
        if (p1Lessons.size() != p2Lessons.size()) {
            List<Lesson> biggerDay = p1Lessons.size() > p2Lessons.size() ? p1Lessons : p2Lessons;
            for (; i < biggerDay.size(); i++) {
                if (random.nextBoolean()) {
                    Lesson lesson = new Lesson(biggerDay.get(i).getTeacher());
                    lesson.setLessonNumber(lessonNumber);
                    offspringLessons.add(lesson);
                    lessonNumber++;
                }
            }
        }
        offspring.setLessons(offspringLessons);
        return offspring;
    }

    private <K, V extends Comparable<? super V>> Map<K, V> sortMapByValue(Map<K, V> map) {
        return map.entrySet().stream()
                .sorted((o1, o2) -> o2.getValue().compareTo(o1.getValue()))
                .collect(Collectors
                        .toMap(Map.Entry::getKey, Map.Entry::getValue, (u, v) -> u, LinkedHashMap::new));
    }
}
