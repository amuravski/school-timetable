package com.solvd.schooltimetable.service.impl;

import com.solvd.schooltimetable.domain.SchoolTimetable;
import com.solvd.schooltimetable.domain.exception.EntityNotFoundException;
import com.solvd.schooltimetable.persistence.SchoolTimetableRepository;
import com.solvd.schooltimetable.persistence.impl.SchoolTimetableMapperImpl;
import com.solvd.schooltimetable.service.ClassTimetableService;
import com.solvd.schooltimetable.service.SchoolTimetableService;

import java.util.List;

public class SchoolTimetableServiceImpl implements SchoolTimetableService {

    private final SchoolTimetableRepository schoolTimetableRepository;

    ClassTimetableService classTimetableService = new ClassTimetableServiceImpl();

    public SchoolTimetableServiceImpl() {
        this.schoolTimetableRepository = new SchoolTimetableMapperImpl();
    }

    @Override
    public void create(SchoolTimetable toCreate) {
        schoolTimetableRepository.create(toCreate);
        toCreate.getClassTimetables().stream()
                .peek(classTimetable -> classTimetable.setSchoolTimetableId(toCreate.getId()))
                .forEach(classTimetable -> classTimetableService.create(classTimetable));
    }

    @Override
    public List<SchoolTimetable> findAll() {
        return schoolTimetableRepository.findAll();
    }

    @Override
    public SchoolTimetable findById(Long id) {
        return schoolTimetableRepository.findById(id)
                .orElseThrow(
                        () -> new EntityNotFoundException(String.format("SchoolTimetable with id [%d] not found", id)));
    }

    @Override
    public void update(SchoolTimetable toUpdate) {
        schoolTimetableRepository.update(toUpdate);
    }

    @Override
    public void delete(SchoolTimetable toDelete) {
        deleteById(toDelete.getId());
    }

    @Override
    public void deleteById(Long id) {
        schoolTimetableRepository.deleteById(id);
    }
}
