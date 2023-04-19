package org.ocm.rest.repository;

import org.ocm.dto.ClassesDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ClassesRepository extends JpaRepository<ClassesDTO, Integer> {
    List<ClassesDTO> findByClassGrade(Integer grade);
}
