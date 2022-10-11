package com.solvd.schooltimetable.persistence.impl;

import com.solvd.schooltimetable.domain.ClassTimetable;
import com.solvd.schooltimetable.persistence.ClassTimetableRepository;
import com.solvd.schooltimetable.persistence.MybatisConfig;
import org.apache.ibatis.session.SqlSession;

import java.util.List;
import java.util.Optional;

public class ClassTimetableMapperImpl implements ClassTimetableRepository {

    @Override
    public void create(ClassTimetable toCreate) {
        try (SqlSession session = MybatisConfig.getSqlSessionFactory().openSession(true)) {
            ClassTimetableRepository mapper = session.getMapper(ClassTimetableRepository.class);
            mapper.create(toCreate);
        }
    }

    @Override
    public List<ClassTimetable> findAll() {
        try (SqlSession session = MybatisConfig.getSqlSessionFactory().openSession(true)) {
            ClassTimetableRepository mapper = session.getMapper(ClassTimetableRepository.class);
            return mapper.findAll();
        }
    }

    @Override
    public Optional<ClassTimetable> findById(Long id) {
        try (SqlSession session = MybatisConfig.getSqlSessionFactory().openSession(true)) {
            ClassTimetableRepository mapper = session.getMapper(ClassTimetableRepository.class);
            return mapper.findById(id);
        }
    }

    @Override
    public void update(ClassTimetable toUpdate) {
        try (SqlSession session = MybatisConfig.getSqlSessionFactory().openSession(true)) {
            ClassTimetableRepository mapper = session.getMapper(ClassTimetableRepository.class);
            mapper.update(toUpdate);
        }
    }

    @Override
    public void deleteById(Long id) {
        try (SqlSession session = MybatisConfig.getSqlSessionFactory().openSession(true)) {
            ClassTimetableRepository mapper = session.getMapper(ClassTimetableRepository.class);
            mapper.deleteById(id);
        }
    }
}
