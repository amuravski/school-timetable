package com.solvd.schooltimetable.persistence;

import java.util.List;
import java.util.Optional;

public interface SchoolTimetableRepository<T> {

    void create(T toCreate);

    List<T> findAll();

    Optional<T> findById(Long id);

    void update(T toUpdate);

    void delete(T toDelete);

    void deleteById(Long id);

}
