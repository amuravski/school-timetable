package com.solvd.schooltimetable.persistence.impl;

import com.solvd.schooltimetable.domain.Teacher;
import com.solvd.schooltimetable.persistence.MybatisConfig;
import com.solvd.schooltimetable.persistence.TeacherRepository;
import org.apache.ibatis.session.SqlSession;

import java.util.List;
import java.util.Optional;

public class TeacherMapperImpl implements TeacherRepository {

    @Override
    public void create(Teacher toCreate) {
        try (SqlSession session = MybatisConfig.getSqlSessionFactory().openSession(true)) {
            TeacherRepository mapper = session.getMapper(TeacherRepository.class);
            mapper.create(toCreate);
        }
    }

    @Override
    public List<Teacher> findAll() {
        try (SqlSession session = MybatisConfig.getSqlSessionFactory().openSession(true)) {
            TeacherRepository mapper = session.getMapper(TeacherRepository.class);
            return mapper.findAll();
        }
    }

    @Override
    public Optional<Teacher> findById(Long id) {
        try (SqlSession session = MybatisConfig.getSqlSessionFactory().openSession(true)) {
            TeacherRepository mapper = session.getMapper(TeacherRepository.class);
            return mapper.findById(id);
        }
    }

    @Override
    public void update(Teacher toUpdate) {
        try (SqlSession session = MybatisConfig.getSqlSessionFactory().openSession(true)) {
            TeacherRepository mapper = session.getMapper(TeacherRepository.class);
            mapper.update(toUpdate);
        }
    }

    @Override
    public void deleteById(Long id) {
        try (SqlSession session = MybatisConfig.getSqlSessionFactory().openSession(true)) {
            TeacherRepository mapper = session.getMapper(TeacherRepository.class);
            mapper.deleteById(id);
        }
    }
}
