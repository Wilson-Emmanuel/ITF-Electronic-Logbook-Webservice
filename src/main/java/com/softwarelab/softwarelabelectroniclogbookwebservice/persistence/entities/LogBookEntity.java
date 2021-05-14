package com.softwarelab.softwarelabelectroniclogbookwebservice.persistence.entities;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;

/**
 * Created by Wilson
 * on Fri, 07/05/2021.
 */
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "logbook_tasks")
public class LogBookEntity extends AbstractBaseEntity<Long>{
    @Column(columnDefinition = "TEXT",nullable = false)
    private String task;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    private StudentEntity student;

    @Column(nullable = false)
    private LocalDate taskDate;
}
