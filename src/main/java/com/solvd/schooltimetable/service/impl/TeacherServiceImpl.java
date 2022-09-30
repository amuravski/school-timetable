package com.solvd.schooltimetable.service.impl;

import com.solvd.schooltimetable.domain.Teacher;
import com.solvd.schooltimetable.domain.exception.EntityNotFoundException;
import com.solvd.schooltimetable.persistence.TeacherRepository;
import com.solvd.schooltimetable.persistence.impl.TeacherMapperImpl;
import com.solvd.schooltimetable.service.TeacherService;

import java.util.List;

public class TeacherServiceImpl implements TeacherService {

    private final TeacherRepository teacherRepository;

    public TeacherServiceImpl() {
        this.teacherRepository = new TeacherMapperImpl();
    }

    @Override
    public void create(Teacher toCreate) {
        teacherRepository.create(toCreate);
    }

    @Override
    public List<Teacher> findAll() {
        return teacherRepository.findAll();
    }

    @Override
    public Teacher findById(Long id) {
        return teacherRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(String.format("Teacher with id [%d] not found", id)));
    }

    @Override
    public void update(Teacher toUpdate) {
        teacherRepository.update(toUpdate);
    }

    @Override
    public void delete(Teacher toDelete) {
        deleteById(toDelete.getId());
    }

    @Override
    public void deleteById(Long id) {
        teacherRepository.deleteById(id);
    }
}
