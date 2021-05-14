package com.softwarelab.softwarelabelectroniclogbookwebservice.persistence.repositories;

import com.softwarelab.softwarelabelectroniclogbookwebservice.persistence.entities.ITFAdminEntity;
import com.softwarelab.softwarelabelectroniclogbookwebservice.persistence.entities.StudentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Created by Wilson
 * on Sun, 09/05/2021.
 */
@Repository
public interface ITFAdminRepository extends JpaRepository<ITFAdminEntity, Integer> {
    boolean existsByStaffNo(String staffNo);
    boolean existsByStaffNoOrEmail(String staffNo, String email);
    Optional<ITFAdminEntity> findByStaffNo(String staffNo);
    boolean existsByEmail(String email);
    Optional<ITFAdminEntity> findByEmail(String email);
}
