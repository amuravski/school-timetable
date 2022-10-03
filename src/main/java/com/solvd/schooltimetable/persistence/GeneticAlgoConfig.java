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
    private final int minLessons;
    private final int maxLessons;
    private final int populationSize;
    private final int percentileThreshold;
    private final boolean elitism;

    public GeneticAlgoConfig(String geneticAlgoConfigName) {
        Properties geneticAlgoConfigProperties = new Properties();
        File propertiesFile = new File(MybatisConfig.class.getClassLoader().getResource(geneticAlgoConfigName).getFile());
        try (FileInputStream f = new FileInputStream(propertiesFile)) {
            geneticAlgoConfigProperties.load(f);
        } catch (IOException e) {
            LOGGER.error(e);
        }
        minWorkDays = Integer.parseInt(geneticAlgoConfigProperties.getProperty("minWorkDays"));
        minLessons = Integer.parseInt(geneticAlgoConfigProperties.getProperty("minLessons"));
        maxLessons = Integer.parseInt(geneticAlgoConfigProperties.getProperty("maxLessons"));
        populationSize = Integer.parseInt(geneticAlgoConfigProperties.getProperty("populationSize"));
        percentileThreshold = Integer.parseInt(geneticAlgoConfigProperties.getProperty("percentileThreshold"));
        elitism = Boolean.parseBoolean(geneticAlgoConfigProperties.getProperty("elitism"));
    }

    public int getMinWorkDays() {
        return minWorkDays;
    }

    public int getMinLessons() {
        return minLessons;
    }

    public int getPopulationSize() {
        return populationSize;
    }

    public int getPercentileThreshold() {
        return percentileThreshold;
    }

    public boolean isElitism() {
        return elitism;
    }

    public int getMaxLessons() {
        return maxLessons;
    }

    @Override
    public String toString() {
        return "GeneticAlgoConfig{" +
                "minWorkDays=" + minWorkDays +
                ", minLessons=" + minLessons +
                ", maxLessons=" + maxLessons +
                ", populationSize=" + populationSize +
                ", percentileThreshold=" + percentileThreshold +
                ", elitism=" + elitism +
                '}';
    }
}
