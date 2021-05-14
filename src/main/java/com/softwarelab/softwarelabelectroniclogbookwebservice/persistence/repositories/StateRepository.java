package com.softwarelab.softwarelabelectroniclogbookwebservice.persistence.repositories;

import com.softwarelab.softwarelabelectroniclogbookwebservice.persistence.entities.StateEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * Created by Wilson
 * on Sun, 09/05/2021.
 */
public interface StateRepository extends JpaRepository<StateEntity,Integer> {
    boolean existsByName(String name);
    Optional<StateEntity> findByName(String name);
}
