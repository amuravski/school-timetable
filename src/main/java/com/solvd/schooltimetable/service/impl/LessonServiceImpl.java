package com.solvd.schooltimetable.service.impl;

import com.solvd.schooltimetable.domain.Lesson;
import com.solvd.schooltimetable.domain.exception.EntityNotFoundException;
import com.solvd.schooltimetable.persistence.LessonRepository;
import com.solvd.schooltimetable.persistence.impl.LessonMapperImpl;
import com.solvd.schooltimetable.service.LessonService;

import java.util.List;

public class LessonServiceImpl implements LessonService {

    private final LessonRepository lessonRepository;

    public LessonServiceImpl() {
        this.lessonRepository = new LessonMapperImpl();
    }

    @Override
    public void create(Lesson toCreate) {
        lessonRepository.create(toCreate);
    }

    @Override
    public List<Lesson> findAll() {
        return lessonRepository.findAll();
    }

    @Override
    public Lesson findById(Long id) {
        return lessonRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(String.format("Lesson with id [%d] not found", id)));
    }

    @Override
    public void update(Lesson toUpdate) {
        lessonRepository.update(toUpdate);
    }

    @Override
    public void delete(Lesson toDelete) {
        deleteById(toDelete.getId());
    }

    @Override
    public void deleteById(Long id) {
        lessonRepository.deleteById(id);
    }
}
