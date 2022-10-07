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

    private final int minWorkDays;
    private final int minLessons;
    private final int maxLessons;
    private final int populationSize;
    private final int elitismPercentileThreshold;
    private final int generationPercentileThreshold;
    private final boolean elitism;
    private final int maxIterations;
    boolean lucky;
    int luckyPercentileThreshold;

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
        elitismPercentileThreshold = Integer.parseInt(geneticAlgoConfigProperties.getProperty("elitismPercentileThreshold"));
        generationPercentileThreshold = Integer.parseInt(geneticAlgoConfigProperties.getProperty("generationPercentileThreshold"));
        elitism = Boolean.parseBoolean(geneticAlgoConfigProperties.getProperty("elitism"));
        maxIterations = Integer.parseInt(geneticAlgoConfigProperties.getProperty("maxIterations"));
    }

    public int getMinWorkDays() {
        return minWorkDays;
    }

    public int getMinLessons() {
        return minLessons;
    }

    public int getMaxLessons() {
        return maxLessons;
    }

    public int getPopulationSize() {
        return populationSize;
    }

    public int getElitismPercentileThreshold() {
        return elitismPercentileThreshold;
    }

    public int getGenerationPercentileThreshold() {
        return generationPercentileThreshold;
    }

    public boolean isElitism() {
        return elitism;
    }

    public int getMaxIterations() {
        return maxIterations;
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
                ", luckyPercentileThreshold=" + luckyPercentileThreshold +
                ", lucky=" + lucky +
                '}';
    }
}
