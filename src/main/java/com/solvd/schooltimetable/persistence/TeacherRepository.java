package com.solvd.schooltimetable.persistence;

import com.solvd.schooltimetable.domain.Teacher;

import java.util.List;

public interface TeacherRepository extends BaseSchoolTimetableRepository<Teacher> {

    List<Teacher> findBySubjectId(Long id);

}
