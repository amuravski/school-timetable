package com.solvd.schooltimetable.persistence.impl;

import com.solvd.schooltimetable.domain.Lesson;
import com.solvd.schooltimetable.persistence.LessonRepository;
import com.solvd.schooltimetable.persistence.MybatisConfig;
import org.apache.ibatis.session.SqlSession;

import java.util.List;
import java.util.Optional;

public class LessonMapperImpl implements LessonRepository {

    @Override
    public void create(Lesson toCreate) {
        try (SqlSession session = MybatisConfig.getSqlSessionFactory().openSession(true)) {
            LessonRepository mapper = session.getMapper(LessonRepository.class);
            mapper.create(toCreate);
        }
    }

    @Override
    public List<Lesson> findAll() {
        try (SqlSession session = MybatisConfig.getSqlSessionFactory().openSession(true)) {
            LessonRepository mapper = session.getMapper(LessonRepository.class);
            return mapper.findAll();
        }
    }

    @Override
    public Optional<Lesson> findById(Long id) {
        try (SqlSession session = MybatisConfig.getSqlSessionFactory().openSession(true)) {
            LessonRepository mapper = session.getMapper(LessonRepository.class);
            return mapper.findById(id);
        }
    }

    @Override
    public void update(Lesson toUpdate) {
        try (SqlSession session = MybatisConfig.getSqlSessionFactory().openSession(true)) {
            LessonRepository mapper = session.getMapper(LessonRepository.class);
            mapper.update(toUpdate);
        }
    }

    @Override
    public void deleteById(Long id) {
        try (SqlSession session = MybatisConfig.getSqlSessionFactory().openSession(true)) {
            LessonRepository mapper = session.getMapper(LessonRepository.class);
            mapper.deleteById(id);
        }
    }
}
