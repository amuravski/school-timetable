package com.solvd.schooltimetable.service.impl;

import com.solvd.schooltimetable.domain.Subject;
import com.solvd.schooltimetable.domain.exception.EntityNotFoundException;
import com.solvd.schooltimetable.persistence.SubjectRepository;
import com.solvd.schooltimetable.persistence.impl.SubjectMapperImpl;
import com.solvd.schooltimetable.service.SubjectService;

import java.util.List;

public class SubjectServiceImpl implements SubjectService {

    private final SubjectRepository subjectRepository;

    public SubjectServiceImpl() {
        this.subjectRepository = new SubjectMapperImpl();
    }

    @Override
    public void create(Subject toCreate) {
        subjectRepository.create(toCreate);
    }

    @Override
    public List<Subject> findAll() {
        return subjectRepository.findAll();
    }

    @Override
    public Subject findById(Long id) {
        return subjectRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(String.format("Subject with id [%d] not found", id)));
    }

    @Override
    public void update(Subject toUpdate) {
        subjectRepository.update(toUpdate);
    }

    @Override
    public void delete(Subject toDelete) {
        deleteById(toDelete.getId());
    }

    @Override
    public void deleteById(Long id) {
        subjectRepository.deleteById(id);
    }
}
