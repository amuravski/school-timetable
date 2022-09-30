package com.solvd.schooltimetable.persistence.impl;

import com.solvd.schooltimetable.domain.Subject;
import com.solvd.schooltimetable.persistence.MybatisConfig;
import com.solvd.schooltimetable.persistence.SubjectRepository;
import org.apache.ibatis.session.SqlSession;

import java.util.List;
import java.util.Optional;

public class SubjectMapperImpl implements SubjectRepository {

    @Override
    public void create(Subject toCreate) {
        try (SqlSession session = MybatisConfig.getSqlSessionFactory().openSession(true)) {
            SubjectRepository mapper = session.getMapper(SubjectRepository.class);
            mapper.create(toCreate);
        }
    }

    @Override
    public List<Subject> findAll() {
        try (SqlSession session = MybatisConfig.getSqlSessionFactory().openSession(true)) {
            SubjectRepository mapper = session.getMapper(SubjectRepository.class);
            return mapper.findAll();
        }
    }

    @Override
    public Optional<Subject> findById(Long id) {
        try (SqlSession session = MybatisConfig.getSqlSessionFactory().openSession(true)) {
            SubjectRepository mapper = session.getMapper(SubjectRepository.class);
            return mapper.findById(id);
        }
    }

    @Override
    public void update(Subject toUpdate) {
        try (SqlSession session = MybatisConfig.getSqlSessionFactory().openSession(true)) {
            SubjectRepository mapper = session.getMapper(SubjectRepository.class);
            mapper.update(toUpdate);
        }
    }

    @Override
    public void deleteById(Long id) {
        try (SqlSession session = MybatisConfig.getSqlSessionFactory().openSession(true)) {
            SubjectRepository mapper = session.getMapper(SubjectRepository.class);
            mapper.deleteById(id);
        }
    }
}
