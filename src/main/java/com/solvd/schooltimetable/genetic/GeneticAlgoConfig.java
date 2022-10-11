package com.solvd.schooltimetable.genetic;

import com.solvd.schooltimetable.persistence.MybatisConfig;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class GeneticAlgoConfig {

    private static final Logger LOGGER = LogManager.getLogger(GeneticAlgoConfig.class);

    private int minWorkDays;
    private int minLessons;
    private int maxLessons;
    private int populationSize;
    private int elitismPercentileThreshold;
    private int generationPercentileThreshold;
    private boolean elitism;
    private int maxIterations;
    private boolean lucky;
    private int luckyPercentileThreshold;
    private boolean mutations;
    private int mutationChance;

    public GeneticAlgoConfig(String geneticAlgoConfigName) {
        Properties geneticAlgoConfigProperties = new Properties();
        String path = MybatisConfig.class.getClassLoader().getResource(geneticAlgoConfigName).getFile();
        if (path.contains("jar!")) {
            path = geneticAlgoConfigName;
        }
        File propertiesFile = new File(path);
        try (FileInputStream f = new FileInputStream(propertiesFile)) {
            geneticAlgoConfigProperties.load(f);
        } catch (IOException e) {
            LOGGER.error(e);
        }
        minWorkDays = Integer.parseInt(geneticAlgoConfigProperties.getProperty("minWorkDays"));
        minLessons = Integer.parseInt(geneticAlgoConfigProperties.getProperty("minLessons"));
        maxLessons = Integer.parseInt(geneticAlgoConfigProperties.getProperty("maxLessons"));
        populationSize = Integer.parseInt(geneticAlgoConfigProperties.getProperty("populationSize"));
        elitismPercentileThreshold = Integer.parseInt(geneticAlgoConfigProperties.getProperty("elitismPercentileThreshold"));
        generationPercentileThreshold = Integer.parseInt(geneticAlgoConfigProperties.getProperty("generationPercentileThreshold"));
        elitism = Boolean.parseBoolean(geneticAlgoConfigProperties.getProperty("elitism"));
        maxIterations = Integer.parseInt(geneticAlgoConfigProperties.getProperty("maxIterations"));
        lucky = Boolean.parseBoolean(geneticAlgoConfigProperties.getProperty("lucky"));
        luckyPercentileThreshold = Integer.parseInt(geneticAlgoConfigProperties.getProperty("luckyPercentileThreshold"));
        mutationChance = Integer.parseInt(geneticAlgoConfigProperties.getProperty("mutationChance"));
        checkArgumentsConfig();
    }

    private void checkArgumentsConfig() {
        if (minWorkDays <= 0) {
            throw new RuntimeException("No working days in a week.");
        }
        if (minWorkDays > 7) {
            throw new RuntimeException("More than 7 days in a week.");
        }
        if (minLessons > maxLessons) {
            throw new RuntimeException("Min quantity of lessons bigger than max quantity of lessons.");
        }
        if (minLessons < 1) {
            throw new RuntimeException("Min quantity of lessons less than 1.");
        }
        if (maxLessons > 9) {
            throw new RuntimeException("Max quantity of lessons more than 9.");
        }
        if (elitismPercentileThreshold < 1 && isElitism()) {
            throw new RuntimeException("ElitismPercentileThreshold is too low.");
        }
        if (generationPercentileThreshold < 1) {
            throw new RuntimeException("GenerationPercentileThreshold is too low.");
        }
        if (luckyPercentileThreshold < 1 && isElitism()) {
            throw new RuntimeException("LuckyPercentileThreshold is too low.");
        }
        if (mutationChance < 1) {
            throw new RuntimeException("MutationChance is too low.");
        }
        if (elitismPercentileThreshold + generationPercentileThreshold +
                luckyPercentileThreshold >= 100) {
            throw new RuntimeException("Common percentiles equal or more than 100.");
        }
        if (mutationChance > 100) {
            throw new RuntimeException("MutationChance more than 100.");
        }
        if (populationSize < 100) {
            setPopulationSize(100);
            LOGGER.info("PopulationSize is too low. PopulationSize changed to: " + populationSize);
        }
    }

    public int getMinWorkDays() {
        return minWorkDays;
    }

    public void setMinWorkDays(int minWorkDays) {
        this.minWorkDays = minWorkDays;
    }

    public int getMinLessons() {
        return minLessons;
    }

    public void setMinLessons(int minLessons) {
        this.minLessons = minLessons;
    }

    public int getMaxLessons() {
        return maxLessons;
    }

    public void setMaxLessons(int maxLessons) {
        this.maxLessons = maxLessons;
    }

    public int getPopulationSize() {
        return populationSize;
    }

    public void setPopulationSize(int populationSize) {
        this.populationSize = populationSize;
    }

    public int getElitismPercentileThreshold() {
        return elitismPercentileThreshold;
    }

    public void setElitismPercentileThreshold(int elitismPercentileThreshold) {
        this.elitismPercentileThreshold = elitismPercentileThreshold;
    }

    public int getGenerationPercentileThreshold() {
        return generationPercentileThreshold;
    }

    public void setGenerationPercentileThreshold(int generationPercentileThreshold) {
        this.generationPercentileThreshold = generationPercentileThreshold;
    }

    public boolean isElitism() {
        return elitism;
    }

    public void setElitism(boolean elitism) {
        this.elitism = elitism;
    }

    public int getMaxIterations() {
        return maxIterations;
    }

    public void setMaxIterations(int maxIterations) {
        this.maxIterations = maxIterations;
    }

    public boolean isLucky() {
        return lucky;
    }

    public void setLucky(boolean lucky) {
        this.lucky = lucky;
    }

    public int getLuckyPercentileThreshold() {
        return luckyPercentileThreshold;
    }

    public void setLuckyPercentileThreshold(int luckyPercentileThreshold) {
        this.luckyPercentileThreshold = luckyPercentileThreshold;
    }

    public boolean isMutations() {
        return mutations;
    }

    public void setMutations(boolean mutations) {
        this.mutations = mutations;
    }

    public int getMutationChance() {
        return mutationChance;
    }

    public void setMutationChance(int mutationChance) {
        this.mutationChance = mutationChance;
    }

    @Override
    public String toString() {
        return "GeneticAlgoConfig{" +
                "minWorkDays=" + minWorkDays +
                ", minLessons=" + minLessons +
                ", maxLessons=" + maxLessons +
                ", populationSize=" + populationSize +
                ", elitismPercentileThreshold=" + elitismPercentileThreshold +
                ", generationPercentileThreshold=" + generationPercentileThreshold +
                ", elitism=" + elitism +
                ", maxIterations=" + maxIterations +
                ", lucky=" + lucky +
                ", luckyPercentileThreshold=" + luckyPercentileThreshold +
                ", mutations=" + mutations +
                ", mutationChance=" + mutationChance +
                '}';
    }
}
