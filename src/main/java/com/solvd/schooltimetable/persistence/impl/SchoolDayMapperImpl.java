package com.solvd.schooltimetable.persistence.impl;

import com.solvd.schooltimetable.domain.SchoolDay;
import com.solvd.schooltimetable.persistence.MybatisConfig;
import com.solvd.schooltimetable.persistence.SchoolDayRepository;
import org.apache.ibatis.session.SqlSession;

import java.util.List;
import java.util.Optional;

public class SchoolDayMapperImpl implements SchoolDayRepository {

    @Override
    public void create(SchoolDay toCreate) {
        try (SqlSession session = MybatisConfig.getSqlSessionFactory().openSession(true)) {
            SchoolDayRepository mapper = session.getMapper(SchoolDayRepository.class);
            mapper.create(toCreate);
        }
    }

    @Override
    public List<SchoolDay> findAll() {
        try (SqlSession session = MybatisConfig.getSqlSessionFactory().openSession(true)) {
            SchoolDayRepository mapper = session.getMapper(SchoolDayRepository.class);
            return mapper.findAll();
        }
    }

    @Override
    public Optional<SchoolDay> findById(Long id) {
        try (SqlSession session = MybatisConfig.getSqlSessionFactory().openSession(true)) {
            SchoolDayRepository mapper = session.getMapper(SchoolDayRepository.class);
            return mapper.findById(id);
        }
    }

    @Override
    public void update(SchoolDay toUpdate) {
        try (SqlSession session = MybatisConfig.getSqlSessionFactory().openSession(true)) {
            SchoolDayRepository mapper = session.getMapper(SchoolDayRepository.class);
            mapper.update(toUpdate);
        }
    }

    @Override
    public void deleteById(Long id) {
        try (SqlSession session = MybatisConfig.getSqlSessionFactory().openSession(true)) {
            SchoolDayRepository mapper = session.getMapper(SchoolDayRepository.class);
            mapper.deleteById(id);
        }
    }
}
