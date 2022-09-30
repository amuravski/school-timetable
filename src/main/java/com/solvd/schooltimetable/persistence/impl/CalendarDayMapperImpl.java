package com.solvd.schooltimetable.persistence.impl;

import com.solvd.schooltimetable.domain.CalendarDay;
import com.solvd.schooltimetable.persistence.CalendarDayRepository;
import com.solvd.schooltimetable.persistence.MybatisConfig;
import org.apache.ibatis.session.SqlSession;

import java.util.List;
import java.util.Optional;

public class CalendarDayMapperImpl implements CalendarDayRepository {

    @Override
    public void create(CalendarDay toCreate) {
        try (SqlSession session = MybatisConfig.getSqlSessionFactory().openSession(true)) {
            CalendarDayRepository mapper = session.getMapper(CalendarDayRepository.class);
            mapper.create(toCreate);
        }
    }

    @Override
    public List<CalendarDay> findAll() {
        try (SqlSession session = MybatisConfig.getSqlSessionFactory().openSession(true)) {
            CalendarDayRepository mapper = session.getMapper(CalendarDayRepository.class);
            return mapper.findAll();
        }
    }

    @Override
    public Optional<CalendarDay> findById(Long id) {
        try (SqlSession session = MybatisConfig.getSqlSessionFactory().openSession(true)) {
            CalendarDayRepository mapper = session.getMapper(CalendarDayRepository.class);
            return mapper.findById(id);
        }
    }

    @Override
    public void update(CalendarDay toUpdate) {
        try (SqlSession session = MybatisConfig.getSqlSessionFactory().openSession(true)) {
            CalendarDayRepository mapper = session.getMapper(CalendarDayRepository.class);
            mapper.update(toUpdate);
        }
    }

    @Override
    public void deleteById(Long id) {
        try (SqlSession session = MybatisConfig.getSqlSessionFactory().openSession(true)) {
            CalendarDayRepository mapper = session.getMapper(CalendarDayRepository.class);
            mapper.deleteById(id);
        }
    }
}
