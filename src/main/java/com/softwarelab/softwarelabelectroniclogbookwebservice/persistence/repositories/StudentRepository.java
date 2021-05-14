package com.softwarelab.softwarelabelectroniclogbookwebservice.persistence.repositories;

import com.softwarelab.softwarelabelectroniclogbookwebservice.persistence.entities.CoordinatorEntity;
import com.softwarelab.softwarelabelectroniclogbookwebservice.persistence.entities.ManagerEntity;
import com.softwarelab.softwarelabelectroniclogbookwebservice.persistence.entities.SchoolEntity;
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
public interface StudentRepository extends JpaRepository<StudentEntity,Long>, JpaSpecificationExecutor<StudentEntity> {
    boolean existsByRegNoAndCoordinator_School(String regNo, SchoolEntity school);
    Optional<StudentEntity> findByRegNoAndCoordinator_School(String regNo, SchoolEntity school);
    List<StudentEntity> findAllByManager(ManagerEntity manager);
    List<StudentEntity> findAllByCoordinator(CoordinatorEntity coordinator);
        boolean existsByEmail(String email);
        Optional<StudentEntity> findByEmail(String email);
}
