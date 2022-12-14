package com.solvd.schooltimetable.genetic;

import com.solvd.schooltimetable.domain.*;
import com.solvd.schooltimetable.util.DoubleStatistics;

import java.util.*;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class GeneticAlgo {

    private final GeneticAlgoConfig geneticAlgoConfig;
    private final List<Double> historicalAverageFitness;
    private List<SchoolClass> schoolClasses;
    private List<Teacher> teachers;
    private List<CalendarDay> calendarDays;
    private SchoolTimetable currentBest;
    private double currentBestFitness;
    private int numberOfSubjectsWithTeachers;
    private Set<Subject> allSubjects;

    public GeneticAlgo(GeneticAlgoConfig geneticAlgoConfig) {
        this.geneticAlgoConfig = geneticAlgoConfig;
        historicalAverageFitness = new ArrayList<>();
    }

    public GeneticAlgoConfig getGeneticAlgoConfig() {
        return geneticAlgoConfig;
    }

    public List<Teacher> getTeachers() {
        return teachers;
    }

    public void setTeachers(List<Teacher> teachers) {
        this.teachers = teachers;
        this.allSubjects = this.teachers.stream()
                .map(Teacher::getSubject)
                .collect(Collectors.toSet());
        this.numberOfSubjectsWithTeachers = this.allSubjects.size();
    }

    public List<SchoolClass> getSchoolClasses() {
        return schoolClasses;
    }

    public void setSchoolClasses(List<SchoolClass> schoolClasses) {
        this.schoolClasses = schoolClasses;
    }

    public void setCalendarDays(List<CalendarDay> calendarDays) {
        this.calendarDays = calendarDays;
    }

    public SchoolTimetable getCurrentBest() {
        return currentBest;
    }

    public void generateAndSetHashcode() {
        currentBest.setHashcode(Objects.hash(
                teachers,
                schoolClasses,
                calendarDays,
                geneticAlgoConfig.getMinWorkDays(),
                geneticAlgoConfig.getMinLessons(),
                geneticAlgoConfig.getMaxLessons()));
    }

    public void run() {
        checkArgumentsAlgo();
        List<SchoolTimetable> population = getPopulation();
        for (int i = 0; i < geneticAlgoConfig.getMaxIterations(); i++) {
            if (i > geneticAlgoConfig.getMaxIterations() / 5 && isGood()) {
                return;
            }
            population = iterateGeneration(population);
        }
    }

    private void checkArgumentsAlgo() {
        GeneticAlgoConfig geneticAlgoConfig = getGeneticAlgoConfig();
        int classNumber = getSchoolClasses().size();
        int teachersAmount = getTeachers().size();
        if ((geneticAlgoConfig.getMinLessons() + geneticAlgoConfig.getMaxLessons()) / 2 * geneticAlgoConfig.getMinWorkDays()
                < numberOfSubjectsWithTeachers
                || geneticAlgoConfig.getMaxLessons() * geneticAlgoConfig.getMinWorkDays()
                < numberOfSubjectsWithTeachers) {
            throw new RuntimeException("Not enough lessons in a week to include all subjects.");
        }
        if (geneticAlgoConfig.getMinLessons() * classNumber > teachersAmount) {
            throw new RuntimeException("Not enough teachers for this number of classes.");
        }
        if (geneticAlgoConfig.getMaxIterations() < 150 * (int) Math.pow(classNumber, 2)) {
            geneticAlgoConfig.setMaxIterations(150 * (int) Math.pow(classNumber, 2));
        }
    }

    public boolean isGood() {
        SchoolTimetable schoolTimetable = currentBest;
        Supplier<Stream<List<SchoolDay>>> daysSupplier = () -> schoolTimetable.getClassTimetables().stream()
                .map(ClassTimetable::getSchoolDays);
        List<List<Lesson>> lessons = daysSupplier.get().map(schoolDays -> schoolDays
                .stream()
                .flatMap(schoolDay -> schoolDay.getLessons().stream())
                .collect(Collectors.toList()))
                .collect(Collectors.toList());
        if (containsNonUniqueSubjects(daysSupplier)) {
            return false;
        }
        if (lessons.stream()
                .map(allClassLessons -> allClassLessons.stream()
                        .map(lesson -> lesson.getTeacher().getSubject())
                        .collect(Collectors.groupingBy(Function.identity(), Collectors.counting())))
                .map(Map::size)
                .anyMatch(numberOfSubjectsInWeek -> numberOfSubjectsInWeek < numberOfSubjectsWithTeachers)) {
            return false;
        }
        if (countParallelTeachers(lessons) > 0) {
            return false;
        }
        return true;
    }

    private Double getFitness(SchoolTimetable schoolTimetable) {
        double fitness;
        Supplier<Stream<List<SchoolDay>>> daysSupplier = () -> schoolTimetable.getClassTimetables().stream()
                .map(ClassTimetable::getSchoolDays);
        List<List<Lesson>> lessons = daysSupplier.get()
                .map(schoolDays -> schoolDays
                        .stream()
                        .flatMap(schoolDay -> schoolDay.getLessons().stream())
                        .collect(Collectors.toList()))
                .collect(Collectors.toList());
        fitness = containsNonUniqueSubjects(daysSupplier) ? -1 : 1;
        fitness -= countParallelTeachers(lessons);
        fitness -= 4 * rateSubjectsDistribution(lessons);
        return fitness;
    }

    private double rateSubjectsDistribution(List<List<Lesson>> lessons) {
        return lessons.stream()
                .map(allClassLessons -> allClassLessons.stream()
                        .map(lesson -> lesson.getTeacher().getSubject())
                        .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()))).
                        peek(subjectLongMap -> allSubjects
                                .forEach(subject -> subjectLongMap
                                        .putIfAbsent(subject, (long) -geneticAlgoConfig.getMaxLessons())))
                .map(subjectLongMap -> computeStandardDeviation(subjectLongMap.values()))
                .reduce(Double::sum)
                .get();
    }

    private int countParallelTeachers(List<List<Lesson>> lessons) {
        int count = 0;
        for (int i = 0; i < lessons.size() - 1; i++) {
            for (int j = i + 1; j < lessons.size(); j++) {
                int ic = 0;
                int jc = 0;
                while (ic < lessons.get(i).size() && jc < lessons.get(j).size()) {
                    if (lessons.get(i).get(ic).getLessonNumber() < lessons.get(j).get(jc).getLessonNumber()) {
                        jc++;
                        continue;
                    }
                    else if (lessons.get(i).get(ic).getLessonNumber() > lessons.get(j).get(jc).getLessonNumber()) {
                        ic++;
                        continue;
                    }
                    if (lessons.get(i).get(ic).getTeacher().equals(lessons.get(j).get(jc).getTeacher())) {
                        count++;
                    }
                    ic++;
                    jc++;
                }
            }
        }
        return count;
    }

    private long countNonUniqueSubjects(Supplier<Stream<List<SchoolDay>>> daysSupplier) {
        return daysSupplier.get().flatMap(Collection::stream)
                .map(schoolDay -> schoolDay.getLessons().stream()
                        .map(lesson -> lesson.getTeacher().getSubject())
                        .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()))
                        .values()
                        .stream()
                        .filter(value -> value > 1)).count();
    }

    private boolean containsNonUniqueSubjects(Supplier<Stream<List<SchoolDay>>> daysSupplier) {
        return daysSupplier.get().flatMap(Collection::stream)
                .anyMatch(schoolDay -> schoolDay.getLessons().stream()
                        .map(lesson -> lesson.getTeacher().getSubject())
                        .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()))
                        .values()
                        .stream()
                        .anyMatch(value -> value > 1));
    }

    private List<SchoolTimetable> iterateGeneration(List<SchoolTimetable> population) {
        List<SchoolTimetable> rated = getRatedPopulation(population);
        currentBest = rated.get(0);
        currentBestFitness = getFitness(rated.get(0));
        List<SchoolTimetable> newPopulation = new ArrayList<>();
        double fraction = population.size() / 100.;
        if (geneticAlgoConfig.isElitism()) {
            newPopulation.addAll(rated.subList(0,
                    (int) (fraction * geneticAlgoConfig.getGenerationPercentileThreshold())));
        }
        List<SchoolTimetable> willCross = new ArrayList<>(rated.subList(0,
                (int) (fraction * geneticAlgoConfig.getElitismPercentileThreshold())));
        List<SchoolTimetable> shuffledWillCross = new ArrayList<>(willCross);
        Collections.shuffle(shuffledWillCross);
        int lastElementIndex = willCross.size() - 1;
        if (willCross.get(lastElementIndex).equals(shuffledWillCross.get(lastElementIndex))) {
            shuffledWillCross.add(0, shuffledWillCross.remove(lastElementIndex));
        }
        if (geneticAlgoConfig.isLucky()) {
            List<SchoolTimetable> luckys = new ArrayList<>(population);
            luckys.removeAll(newPopulation);
            luckys.removeAll(willCross);
            Collections.shuffle(population);
            newPopulation.addAll(luckys.stream()
                    .limit((int) (fraction * geneticAlgoConfig.getLuckyPercentileThreshold()))
                    .map(lucky -> getOffspring(lucky,
                            shuffledWillCross.get((int) (Math.random() * shuffledWillCross.size()))))
                    .collect(Collectors.toList()));
        }
        willCross.forEach(firstParent -> {
            if (shuffledWillCross.size() > 0) {
                SchoolTimetable secondParent;
                if (!firstParent.equals(shuffledWillCross.get(0))) {
                    secondParent = shuffledWillCross.remove(0);
                }
                else {
                    secondParent = shuffledWillCross.remove(1);
                }
                SchoolTimetable offspring = getOffspring(firstParent, secondParent);
                newPopulation.add(offspring);
            }
        });
        complementPopulation(newPopulation);
        return newPopulation;
    }

    private List<SchoolTimetable> getRatedPopulation(List<SchoolTimetable> population) {
        Map<SchoolTimetable, Double> sortedMap = sortMapByValue(population
                .stream()
                .collect(Collectors.toMap(schoolTimetable -> schoolTimetable, this::getFitness, (u, v) -> u,
                        HashMap::new)));
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
        List<ClassTimetable> classTimetables = new ArrayList<>();
        Random rand = new Random();
        for (SchoolClass schoolClass : schoolClasses) {
            List<SchoolDay> schoolDays = new ArrayList<>();
            ClassTimetable classTimetable = new ClassTimetable(schoolClass);
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
            classTimetable.setSchoolDays(schoolDays);
            classTimetables.add(classTimetable);
        }
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
        int size = historicalAverageFitness.size() - 1;
        boolean mutations = size > 2
                && historicalAverageFitness.get(size) - historicalAverageFitness.get(size - 2) <= 0;
        int i;
        int lessonNumber = 1;
        for (i = 0; i < Integer.min(p1Lessons.size(), p2Lessons.size()); i++) {
            Lesson lesson = new Lesson((random.nextBoolean() ? p1Lessons.get(i) : p2Lessons.get(i)).getTeacher());
            if (mutations) {
                int chance = random.nextInt(100);
                if (chance < geneticAlgoConfig.getMutationChance()) {
                    Teacher randomTeacher = teachers.get(random.nextInt(teachers.size()));
                    lesson.setTeacher(randomTeacher);
                }
            }
            lesson.setLessonNumber(lessonNumber);
            offspringLessons.add(lesson);
            lessonNumber++;
        }

        if (p1Lessons.size() != p2Lessons.size()) {
            List<Lesson> biggerDay = p1Lessons.size() > p2Lessons.size() ? p1Lessons : p2Lessons;
            for (; i < biggerDay.size(); i++) {
                Lesson lesson = new Lesson();
                if (mutations) {
                    int chance = random.nextInt(100);
                    if (chance < geneticAlgoConfig.getMutationChance()) {
                        if (random.nextBoolean()) {
                            Teacher randomTeacher = teachers.get(random.nextInt(teachers.size()));
                            lesson.setTeacher(randomTeacher);
                            lesson.setLessonNumber(lessonNumber);
                            lessonNumber++;
                            offspringLessons.add(lesson);
                        }
                    }
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
