package com.softwarelab.softwarelabelectroniclogbookwebservice.persistence.repositories;

import com.softwarelab.softwarelabelectroniclogbookwebservice.persistence.entities.CoordinatorEntity;
import com.softwarelab.softwarelabelectroniclogbookwebservice.persistence.entities.SchoolEntity;
import com.softwarelab.softwarelabelectroniclogbookwebservice.persistence.entities.StateEntity;
import com.softwarelab.softwarelabelectroniclogbookwebservice.persistence.entities.StudentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Created by Wilson
 * on Sun, 09/05/2021.
 */
@Repository
public interface CoordinatorRepository extends JpaRepository<CoordinatorEntity,Long>, JpaSpecificationExecutor<CoordinatorEntity> {
    List<CoordinatorEntity> findAllBySchool(SchoolEntity school);
    List<CoordinatorRepository> findAllBySchool_State(StateEntity states);
    boolean existsByEmail(String email);
    Optional<CoordinatorEntity> findByEmail(String email);
}
