package com.solvd.schooltimetable.persistence.impl;

import com.solvd.schooltimetable.domain.SchoolTimetable;
import com.solvd.schooltimetable.persistence.MybatisConfig;
import com.solvd.schooltimetable.persistence.SchoolTimetableRepository;
import org.apache.ibatis.session.SqlSession;

import java.util.List;
import java.util.Optional;

public class SchoolTimetableMapperImpl implements SchoolTimetableRepository {

    @Override
    public void create(SchoolTimetable toCreate) {
        try (SqlSession session = MybatisConfig.getSqlSessionFactory().openSession(true)) {
            SchoolTimetableRepository mapper = session.getMapper(SchoolTimetableRepository.class);
            mapper.create(toCreate);
        }
    }

    @Override
    public List<SchoolTimetable> findAll() {
        try (SqlSession session = MybatisConfig.getSqlSessionFactory().openSession(true)) {
            SchoolTimetableRepository mapper = session.getMapper(SchoolTimetableRepository.class);
            return mapper.findAll();
        }
    }

    @Override
    public Optional<SchoolTimetable> findById(Long id) {
        try (SqlSession session = MybatisConfig.getSqlSessionFactory().openSession(true)) {
            SchoolTimetableRepository mapper = session.getMapper(SchoolTimetableRepository.class);
            return mapper.findById(id);
        }
    }

    @Override
    public void update(SchoolTimetable toUpdate) {
        try (SqlSession session = MybatisConfig.getSqlSessionFactory().openSession(true)) {
            SchoolTimetableRepository mapper = session.getMapper(SchoolTimetableRepository.class);
            mapper.update(toUpdate);
        }
    }

    @Override
    public void deleteById(Long id) {
        try (SqlSession session = MybatisConfig.getSqlSessionFactory().openSession(true)) {
            SchoolTimetableRepository mapper = session.getMapper(SchoolTimetableRepository.class);
            mapper.deleteById(id);
        }
    }
}
