package com.solvd.schooltimetable.persistence;

import java.util.List;
import java.util.Optional;

public interface BaseSchoolTimetableRepository<T> {

    void create(T toCreate);

    List<T> findAll();

    Optional<T> findById(Long id);

    void update(T toUpdate);

    void deleteById(Long id);

}
