package org.ocm.rest.repository;

import org.ocm.dto.auth.AppUserDTO;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AuthRepository extends CrudRepository<AppUserDTO, String> {
}
