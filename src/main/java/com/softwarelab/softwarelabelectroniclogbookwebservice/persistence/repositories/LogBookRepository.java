package com.softwarelab.softwarelabelectroniclogbookwebservice.persistence.repositories;

import com.softwarelab.softwarelabelectroniclogbookwebservice.persistence.entities.LogBookEntity;
import com.softwarelab.softwarelabelectroniclogbookwebservice.persistence.entities.StudentEntity;
import org.springframework.data.domain.Sort;
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
public interface LogBookRepository extends JpaRepository<LogBookEntity, Long> {
    List<LogBookEntity> findAllByStudent(StudentEntity student, Sort sort);
    Optional<LogBookEntity> findByStudentAndTaskDate(StudentEntity student, LocalDate taskDate);
    int countAllByStudentAndTaskDateIsBetween(StudentEntity student, LocalDate startDate, LocalDate endDate);

}
