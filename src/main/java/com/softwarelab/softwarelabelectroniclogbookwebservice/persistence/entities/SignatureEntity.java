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
@Table(name = "signatures")
public class SignatureEntity extends AbstractBaseEntity<Long>{
    @Column
    private LocalDate startTaskDate;

    @Column
    private LocalDate endTaskDate;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    private StudentEntity student;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    private ManagerEntity manager;
}
