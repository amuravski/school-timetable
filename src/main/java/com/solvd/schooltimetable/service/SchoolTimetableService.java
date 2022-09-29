package com.solvd.schooltimetable.service;

import java.util.List;

public interface SchoolTimetableService<T> {

    void create(T toCreate);

    List<T> findAll();

    T findById(Long id);

    void update(T toUpdate);

    void delete(T toDelete);

    void deleteById(Long id);

}
