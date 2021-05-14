package com.softwarelab.softwarelabelectroniclogbookwebservice.persistence.repositories;

import com.softwarelab.softwarelabelectroniclogbookwebservice.persistence.entities.ManagerEntity;
import com.softwarelab.softwarelabelectroniclogbookwebservice.persistence.entities.SignatureEntity;
import com.softwarelab.softwarelabelectroniclogbookwebservice.persistence.entities.StudentEntity;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

/**
 * Created by Wilson
 * on Mon, 10/05/2021.
 */
@Repository
public interface SignatureRepository extends JpaRepository<SignatureEntity,Long> {
    Optional<SignatureEntity> findByStudentAndStartTaskDateAndEndTaskDate(StudentEntity student, LocalDate startDate, LocalDate endDate);
    boolean existsByStudentAndStartTaskDateAndEndTaskDate(StudentEntity student, LocalDate startDate, LocalDate endDate);
    List<SignatureEntity> findAllByManagerAndStudent(ManagerEntity manager, StudentEntity student , Pageable pageable);
}
