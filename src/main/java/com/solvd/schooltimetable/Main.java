package com.solvd.schooltimetable;

import com.solvd.schooltimetable.domain.Subject;
import com.solvd.schooltimetable.domain.Teacher;
import com.solvd.schooltimetable.persistence.MybatisConfig;
import com.solvd.schooltimetable.service.SubjectService;
import com.solvd.schooltimetable.service.TeacherService;
import com.solvd.schooltimetable.service.impl.SubjectServiceImpl;
import com.solvd.schooltimetable.service.impl.TeacherServiceImpl;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Main {

    private static final Logger LOGGER = LogManager.getLogger(Main.class);

    public static void main(String[] args) {

        TeacherService teacherService = new TeacherServiceImpl();
        SubjectService subjectService = new SubjectServiceImpl();

        Subject subject = new Subject();
        subject.setName("Subj1");
        Teacher teacher = new Teacher();
        teacher.setSubject(subject);
        teacher.setFullName("TEST");

        teacherService.create(teacher);

        System.exit(0);

        LOGGER.info(teacherService.findById(1L));

        subject = subjectService.findById(1L);
        teacher.setSubject(subject);
        teacher.setFullName("UPDATED");
        teacherService.update(teacher);
        LOGGER.info(teacherService.findById(teacher.getId()));
        teacherService.delete(teacher);
    }
}
