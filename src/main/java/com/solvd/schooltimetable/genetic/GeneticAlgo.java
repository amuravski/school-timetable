package com.solvd.schooltimetable.genetic;

import com.solvd.schooltimetable.domain.*;
import com.solvd.schooltimetable.util.DoubleStatistics;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class GeneticAlgo {

    private final GeneticAlgoConfig geneticAlgoConfig;
    private final List<Double> historicalAverageFitness;
    private List<SchoolClass> schoolClasses;
    private List<Teacher> teachers;
    private List<CalendarDay> calendarDays;
    private SchoolTimetable currentBest;
    private double currentBestFitness;
    private int luckyPercentileThreshold;

    public GeneticAlgo(GeneticAlgoConfig geneticAlgoConfig) {
        this.geneticAlgoConfig = geneticAlgoConfig;
        historicalAverageFitness = new ArrayList<>();
    }

    public SchoolTimetable getCurrentBest() {
        return currentBest;
    }

    public double getCurrentBestFitness() {
        return currentBestFitness;
    }

    public void run() {
        List<SchoolTimetable> population = getPopulation();
        for (int i = 0; i < geneticAlgoConfig.getMaxIterations(); i++) {
            if (i != 0 && getFitness(currentBest) == Double.POSITIVE_INFINITY) {
                return;
            }
            population = iterateGeneration(population);
        }
    }

    private Double getFitness(SchoolTimetable schoolTimetable) {
        List<List<Lesson>> lessons = schoolTimetable.getClassTimetables().stream()
                .map(ClassTimetable::getSchoolDays).map(schoolDays ->
                        schoolDays.stream().flatMap(schoolDay -> schoolDay.getLessons().stream()).collect(Collectors.toList())
                ).collect(Collectors.toList());
        for (int i = 0; i < lessons.size() - 1; i++) {
            for (int j = i + 1; j < lessons.size(); j++) {
                int ic = 0;
                int jc = 0;
                while (ic < lessons.get(i).size() && jc < lessons.get(j).size()) {
                    if (lessons.get(i).get(ic).getLessonNumber() < lessons.get(j).get(jc).getLessonNumber()) {
                        jc++;
                        continue;
                    }
                    if (lessons.get(i).get(ic).getLessonNumber() > lessons.get(j).get(jc).getLessonNumber()) {
                        ic++;
                        continue;
                    }
                    if (lessons.get(i).get(ic).getTeacher().equals(lessons.get(j).get(jc).getTeacher())) {
                        return Double.NEGATIVE_INFINITY;
                    }
                    ic++;
                    jc++;
                }
            }
        }
        return 1 / lessons.stream()
                .map(lessons1 -> lessons1.stream()
                        .map(lesson -> lesson.getTeacher().getSubject()).collect(Collectors.groupingBy(Function.identity(), Collectors.counting()))
                ).map(subjectLongMap -> computeStandardDeviation(subjectLongMap.values()))
                .reduce(Double::sum).get();
    }

    private List<SchoolTimetable> iterateGeneration(List<SchoolTimetable> population) {
        List<SchoolTimetable> rated = getRatedPopulation(population);
        currentBest = rated.get(0);
        currentBestFitness = getFitness(rated.get(0));
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
        
        List<SchoolTimetable> luckys = new ArrayList<>();
        luckys.addAll(population);
        luckys.removeAll(newPopulation);
        luckys.removeAll(willCross);
        int n = population.size() * luckyPercentileThreshold;
        Collections.shuffle(population);
        luckys = population.stream().limit(n).collect(Collectors.toList());
        newPopulation.addAll(
                luckys.stream()
                        .map(lucky -> getOffspring(lucky, willCross.get((int) (Math.random() * n))))
                        .collect(Collectors.toList())
        );
        
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

    private List<SchoolTimetable> getRatedPopulation(List<SchoolTimetable> population) {
        Map<SchoolTimetable, Double> sortedMap = sortMapByValue(population
                .stream()
                .collect(Collectors.toMap(schoolTimetable -> schoolTimetable, this::getFitness, (u, v) -> u, HashMap::new)));
        double averageFitness = sortedMap.values().stream()
                .mapToDouble(i -> i)
                .filter(Double::isFinite)
                .average()
                .getAsDouble();
        historicalAverageFitness.add(averageFitness);
        return new ArrayList<>(sortedMap.keySet());
    }

    private SchoolTimetable getOffspring(SchoolTimetable p1, SchoolTimetable p2) {
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

    private SchoolTimetable getRandomSchoolTimetable() {
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

    private List<SchoolTimetable> getPopulation() {
        List<SchoolTimetable> schoolTimetables = new ArrayList<>();
        for (int i = 0; i < geneticAlgoConfig.getPopulationSize(); i++) {
            schoolTimetables.add(getRandomSchoolTimetable());
        }
        return schoolTimetables;
    }

    private void complementPopulation(List<SchoolTimetable> schoolTimetables) {
        int currentPopulationSize = schoolTimetables.size();
        int complementPopulationSize = geneticAlgoConfig.getPopulationSize() - currentPopulationSize;
        IntStream.range(0, complementPopulationSize)
                .forEach((i) -> schoolTimetables.add(getRandomSchoolTimetable()));
    }

    public void setSchoolClasses(List<SchoolClass> schoolClasses) {
        this.schoolClasses = schoolClasses;
    }

    public void setTeachers(List<Teacher> teachers) {
        this.teachers = teachers;
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

    private double computeStandardDeviation(Collection<Long> collection) {
        return collection.stream()
                .map(Number::doubleValue)
                .collect(DoubleStatistics.collector())
                .getStandardDeviation();
    }

    private <K, V extends Comparable<? super V>> Map<K, V> sortMapByValue(Map<K, V> map) {
        return map.entrySet().stream()
                .sorted((o1, o2) -> o2.getValue().compareTo(o1.getValue()))
                .collect(Collectors
                        .toMap(Map.Entry::getKey, Map.Entry::getValue, (u, v) -> u, LinkedHashMap::new));
    }
}
