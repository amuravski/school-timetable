package com.solvd.schooltimetable.service;

import com.solvd.schooltimetable.domain.Teacher;

import java.util.List;

public interface TeacherService extends BaseSchoolTimetableService<Teacher> {

    List<Teacher> findBySubjectId(Long id);

}
