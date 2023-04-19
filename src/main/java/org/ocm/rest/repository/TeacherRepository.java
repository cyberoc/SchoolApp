package org.ocm.rest.repository;

import org.ocm.dto.ClassesDTO;
import org.ocm.dto.TeacherDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TeacherRepository extends JpaRepository<TeacherDTO, Integer> {
    TeacherDTO findByFirstname(String firstname);
}
