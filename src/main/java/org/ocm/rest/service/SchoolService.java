package org.ocm.rest.service;

import lombok.extern.slf4j.Slf4j;
import org.ocm.dto.StudentDTO;
import org.ocm.dto.TeacherDTO;
import org.ocm.rest.repository.ClassesRepository;
import org.ocm.rest.repository.StudentRepository;
import org.ocm.rest.repository.TeacherRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class SchoolService {

    private final StudentRepository studentRepository;

    private final ClassesRepository classesRepository;

    private final TeacherRepository teacherRepository;

    @Autowired
    public SchoolService(StudentRepository studentRepository,
                         ClassesRepository classesRepository,
                         TeacherRepository teacherRepository) {
        this.studentRepository = studentRepository;
        this.classesRepository = classesRepository;
        this.teacherRepository = teacherRepository;
    }

    public Page<StudentDTO> getFullListOfStudents(Pageable paging){
        return studentRepository.findAll(paging);
    }

    public TeacherDTO getListByTeacher(String teacherFN){
        return teacherRepository.findByFirstname(teacherFN);
    }

}
