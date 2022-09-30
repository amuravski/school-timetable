package com.solvd.schooltimetable.persistence.impl;

import com.solvd.schooltimetable.domain.SchoolClass;
import com.solvd.schooltimetable.persistence.MybatisConfig;
import com.solvd.schooltimetable.persistence.SchoolClassRepository;
import org.apache.ibatis.session.SqlSession;

import java.util.List;
import java.util.Optional;

public class SchoolClassMapperImpl implements SchoolClassRepository {

    @Override
    public void create(SchoolClass toCreate) {
        try (SqlSession session = MybatisConfig.getSqlSessionFactory().openSession(true)) {
            SchoolClassRepository mapper = session.getMapper(SchoolClassRepository.class);
            mapper.create(toCreate);
        }
    }

    @Override
    public List<SchoolClass> findAll() {
        try (SqlSession session = MybatisConfig.getSqlSessionFactory().openSession(true)) {
            SchoolClassRepository mapper = session.getMapper(SchoolClassRepository.class);
            return mapper.findAll();
        }
    }

    @Override
    public Optional<SchoolClass> findById(Long id) {
        try (SqlSession session = MybatisConfig.getSqlSessionFactory().openSession(true)) {
            SchoolClassRepository mapper = session.getMapper(SchoolClassRepository.class);
            return mapper.findById(id);
        }
    }

    @Override
    public void update(SchoolClass toUpdate) {
        try (SqlSession session = MybatisConfig.getSqlSessionFactory().openSession(true)) {
            SchoolClassRepository mapper = session.getMapper(SchoolClassRepository.class);
            mapper.update(toUpdate);
        }
    }

    @Override
    public void deleteById(Long id) {
        try (SqlSession session = MybatisConfig.getSqlSessionFactory().openSession(true)) {
            SchoolClassRepository mapper = session.getMapper(SchoolClassRepository.class);
            mapper.deleteById(id);
        }
    }
}
