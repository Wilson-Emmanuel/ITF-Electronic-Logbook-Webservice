package com.softwarelab.softwarelabelectroniclogbookwebservice.persistence.repositories;

import com.softwarelab.softwarelabelectroniclogbookwebservice.persistence.entities.ManagerEntity;
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
public interface ManagerRepository extends JpaRepository<ManagerEntity, Long>, JpaSpecificationExecutor<ManagerEntity> {
    List<ManagerEntity> findAllByState(StateEntity state);
    boolean existsByEmail(String email);
    Optional<ManagerEntity> findByEmail(String email);
}
