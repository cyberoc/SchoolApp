package org.ocm.rest.controller;

import lombok.extern.slf4j.Slf4j;
import org.ocm.dto.StudentDTO;
import org.ocm.dto.TeacherDTO;
import org.ocm.rest.service.SchoolService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/api")
public class SchoolController {

    private final SchoolService schoolService;

    @Autowired
    public SchoolController(SchoolService schoolService) {
        this.schoolService = schoolService;
    }

    @GetMapping("/getAllStudents")
    public ResponseEntity<List<StudentDTO>> getAllStudents(@RequestParam(defaultValue = "0") int page,
                                                           @RequestParam(defaultValue = "1") int size) {
        List<StudentDTO> allStudents = new ArrayList<StudentDTO>();
        Pageable paging = PageRequest.of(page, size);

        Page<StudentDTO> pages = schoolService.getFullListOfStudents(paging);

        if (pages.isEmpty()) {return new ResponseEntity<>(HttpStatus.NO_CONTENT);}

        Map<String, Object> response = new HashMap<>();
        response.put("students", pages.getContent());
        response.put("currentPage", pages.getNumber());
        response.put("totalItems", pages.getTotalElements());
        response.put("totalPages", pages.getTotalPages());

        return new ResponseEntity(response, HttpStatus.OK);
    }

    @GetMapping("/getStudentsByTeacher")
    public ResponseEntity<List<TeacherDTO>> getAllStudents(@RequestParam String teacherFirstname) {
        TeacherDTO studentByTeacher = schoolService.getListByTeacher(teacherFirstname);
        if (studentByTeacher==null) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        return new ResponseEntity(studentByTeacher, HttpStatus.OK);
    }

    @GetMapping("/status")
    @ResponseStatus(HttpStatus.OK)
    public String status(){
        return "Running !!!";
    }
}
