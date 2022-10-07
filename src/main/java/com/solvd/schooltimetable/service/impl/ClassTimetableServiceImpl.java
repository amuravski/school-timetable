package com.solvd.schooltimetable.service.impl;

import com.solvd.schooltimetable.domain.ClassTimetable;
import com.solvd.schooltimetable.domain.exception.EntityNotFoundException;
import com.solvd.schooltimetable.persistence.ClassTimetableRepository;
import com.solvd.schooltimetable.persistence.impl.ClassTimetableMapperImpl;
import com.solvd.schooltimetable.service.ClassTimetableService;
import com.solvd.schooltimetable.service.SchoolDayService;

import java.util.List;

public class ClassTimetableServiceImpl implements ClassTimetableService {

    private final ClassTimetableRepository classTimetableRepository;

    SchoolDayService schoolDayService = new SchoolDayServiceImpl();

    public ClassTimetableServiceImpl() {
        this.classTimetableRepository = new ClassTimetableMapperImpl();
    }

    @Override
    public void create(ClassTimetable toCreate) {
        classTimetableRepository.create(toCreate);
        toCreate.getSchoolDays().stream()
                .peek(schoolDay -> schoolDay.setClassTimetableId(toCreate.getId()))
                .forEach(schoolDay -> schoolDayService.create(schoolDay));
    }

    @Override
    public List<ClassTimetable> findAll() {
        return classTimetableRepository.findAll();
    }

    @Override
    public ClassTimetable findById(Long id) {
        return classTimetableRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(String.format("ClassTimetable with id [%d] not found", id)));
    }

    @Override
    public void update(ClassTimetable toUpdate) {
        classTimetableRepository.update(toUpdate);
        toUpdate.getSchoolDays().stream()
                .peek(schoolDay -> schoolDay.setClassTimetableId(toUpdate.getId()))
                .forEach(schoolDay -> schoolDayService.update(schoolDay));
    }

    @Override
    public void delete(ClassTimetable toDelete) {
        deleteById(toDelete.getId());
    }

    @Override
    public void deleteById(Long id) {
        classTimetableRepository.deleteById(id);
    }
}
