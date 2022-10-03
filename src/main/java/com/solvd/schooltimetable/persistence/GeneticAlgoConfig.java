package com.solvd.schooltimetable.persistence;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class GeneticAlgoConfig {

    private static final Logger LOGGER = LogManager.getLogger(GeneticAlgoConfig.class);

    private final int minWorkDays;
    private final int minLesson;
    private final int maxLesson;
    private final int population;
    private final int percentileThreshold;
    private final boolean elitism;

    public GeneticAlgoConfig(String geneticAlgoConfigName) {
        Properties geneticAlgoConfigProperties = new Properties();
        File propertiesFile = new File(geneticAlgoConfigName);
        try (FileInputStream f = new FileInputStream(propertiesFile)) {
            geneticAlgoConfigProperties.load(f);
        } catch (IOException e) {
            LOGGER.error(e);
        }
        minWorkDays = Integer.parseInt(geneticAlgoConfigProperties.getProperty("minWorkDays"));
        minLesson = Integer.parseInt(geneticAlgoConfigProperties.getProperty("minLesson"));
        maxLesson = Integer.parseInt(geneticAlgoConfigProperties.getProperty("maxLesson"));
        population = Integer.parseInt(geneticAlgoConfigProperties.getProperty("population"));
        percentileThreshold = Integer.parseInt(geneticAlgoConfigProperties.getProperty("percentileThreshold"));
        elitism = Boolean.parseBoolean(geneticAlgoConfigProperties.getProperty("elitism"));
    }

    public int getMinWorkDays() {
        return minWorkDays;
    }

    public int getMinLesson() {
        return minLesson;
    }

    public int getPopulation() {
        return population;
    }

    public int getPercentileThreshold() {
        return percentileThreshold;
    }

    public boolean isElitism() {
        return elitism;
    }

    public int getMaxLesson() {
        return maxLesson;
    }

    @Override
    public String toString() {
        return "GeneticAlgoConfig{" +
                "minWorkDays=" + minWorkDays +
                ", minLesson=" + minLesson +
                ", maxLesson=" + maxLesson +
                ", population=" + population +
                ", percentileThreshold=" + percentileThreshold +
                ", elitism=" + elitism +
                '}';
    }
}
