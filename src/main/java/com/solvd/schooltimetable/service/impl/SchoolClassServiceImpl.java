package com.solvd.schooltimetable.service.impl;

import com.solvd.schooltimetable.domain.SchoolClass;
import com.solvd.schooltimetable.domain.exception.EntityNotFoundException;
import com.solvd.schooltimetable.persistence.SchoolClassRepository;
import com.solvd.schooltimetable.persistence.impl.SchoolClassMapperImpl;
import com.solvd.schooltimetable.service.SchoolClassService;

import java.util.List;

public class SchoolClassServiceImpl implements SchoolClassService {

    private final SchoolClassRepository schoolClassRepository;

    public SchoolClassServiceImpl() {
        this.schoolClassRepository = new SchoolClassMapperImpl();
    }

    @Override
    public void create(SchoolClass toCreate) {
        schoolClassRepository.create(toCreate);
    }

    @Override
    public List<SchoolClass> findAll() {
        return schoolClassRepository.findAll();
    }

    @Override
    public SchoolClass findById(Long id) {
        return schoolClassRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(String.format("SchoolClass with id [%d] not found", id)));
    }

    @Override
    public void update(SchoolClass toUpdate) {
        schoolClassRepository.update(toUpdate);
    }

    @Override
    public void delete(SchoolClass toDelete) {
        deleteById(toDelete.getId());
    }

    @Override
    public void deleteById(Long id) {
        schoolClassRepository.deleteById(id);
    }
}
