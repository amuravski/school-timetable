package com.solvd.schooltimetable.service.impl;

import com.solvd.schooltimetable.domain.SchoolDay;
import com.solvd.schooltimetable.domain.exception.EntityNotFoundException;
import com.solvd.schooltimetable.persistence.SchoolDayRepository;
import com.solvd.schooltimetable.persistence.impl.SchoolDayMapperImpl;
import com.solvd.schooltimetable.service.SchoolDayService;

import java.util.List;

public class SchoolDayServiceImpl implements SchoolDayService {

    private final SchoolDayRepository schoolDayRepository;

    public SchoolDayServiceImpl() {
        this.schoolDayRepository = new SchoolDayMapperImpl();
    }

    @Override
    public void create(SchoolDay toCreate) {
        schoolDayRepository.create(toCreate);
    }

    @Override
    public List<SchoolDay> findAll() {
        return schoolDayRepository.findAll();
    }

    @Override
    public SchoolDay findById(Long id) {
        return schoolDayRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(String.format("SchoolDay with id [%d] not found", id)));
    }

    @Override
    public void update(SchoolDay toUpdate) {
        schoolDayRepository.update(toUpdate);
    }

    @Override
    public void delete(SchoolDay toDelete) {
        deleteById(toDelete.getId());
    }

    @Override
    public void deleteById(Long id) {
        schoolDayRepository.deleteById(id);
    }
}
