package com.solvd.schooltimetable.service.impl;

import com.solvd.schooltimetable.domain.CalendarDay;
import com.solvd.schooltimetable.domain.exception.EntityNotFoundException;
import com.solvd.schooltimetable.persistence.CalendarDayRepository;
import com.solvd.schooltimetable.persistence.impl.CalendarDayMapperImpl;
import com.solvd.schooltimetable.service.CalendarDayService;

import java.util.List;

public class CalendarDayServiceImpl implements CalendarDayService {

    private final CalendarDayRepository calendarDayRepository;

    public CalendarDayServiceImpl() {
        this.calendarDayRepository = new CalendarDayMapperImpl();
    }

    @Override
    public void create(CalendarDay toCreate) {
        calendarDayRepository.create(toCreate);
    }

    @Override
    public List<CalendarDay> findAll() {
        return calendarDayRepository.findAll();
    }

    @Override
    public CalendarDay findById(Long id) {
        return calendarDayRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(String.format("CalendarDay with id [%d] not found", id)));
    }

    @Override
    public void update(CalendarDay toUpdate) {
        calendarDayRepository.update(toUpdate);
    }

    @Override
    public void delete(CalendarDay toDelete) {
        deleteById(toDelete.getId());
    }

    @Override
    public void deleteById(Long id) {
        calendarDayRepository.deleteById(id);
    }
}
