package com.softwarelab.softwarelabelectroniclogbookwebservice.persistence.repositories;

import com.softwarelab.softwarelabelectroniclogbookwebservice.persistence.entities.SchoolEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Created by Wilson
 * on Sun, 09/05/2021.
 */
@Repository
public interface SchoolRepository extends JpaRepository<SchoolEntity, Integer>, JpaSpecificationExecutor<SchoolEntity> {
    boolean existsByName(String name);//Name must be unique
    Optional<SchoolEntity> findByName(String name);
}
