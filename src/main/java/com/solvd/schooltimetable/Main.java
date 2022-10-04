package com.solvd.schooltimetable;


import com.solvd.schooltimetable.domain.SchoolTimetable;

import com.solvd.schooltimetable.persistence.GenericAlgo;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.List;

public class Main {

    private static final Logger LOGGER = LogManager.getLogger(Main.class);

    public static void main(String[] args) {
        SchoolTimetable schoolTimetable = new SchoolTimetable();
        schoolTimetable = GenericAlgo.getRandomSchoolTimetable(2, 6, 6);
        LOGGER.info(schoolTimetable);
//        SchoolTimetableService schoolTimetableService = new SchoolTimetableServiceImpl();
//        schoolTimetableService.create(schoolTimetable);
    }
}
