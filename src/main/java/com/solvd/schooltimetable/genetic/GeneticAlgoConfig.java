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
