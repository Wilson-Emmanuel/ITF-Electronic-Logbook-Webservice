package com.softwarelab.softwarelabelectroniclogbookwebservice.persistence.entities;

import com.softwarelab.softwarelabelectroniclogbookwebservice.services.models.enums.UserType;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

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
@Table(name = "students")
public class StudentEntity extends UserEntity<Long>{
    @Column(unique = true,nullable = false)
    private String regNo;

    @Column
    private String bankName;

    @Column
    private String accountName;

    @Column
    private String accountNumber;

    @Column
    private String bankSortCode;

    @ManyToOne(fetch = FetchType.LAZY,optional = false)
    private CoordinatorEntity coordinator;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    private ManagerEntity manager;//no registration without manager

    @Column(columnDefinition = "TEXT")
    private String coordinatorRemarks;

    @Column
    private LocalDate startDate;//set by cordinator

    @Column
    @Builder.Default
    private Boolean logBookSigned = Boolean.FALSE;

    @Column
    @Builder.Default
    private Boolean paid = Boolean.FALSE;

    @Column(nullable = false)
    @Builder.Default
    @Enumerated(EnumType.STRING)
    private UserType userType = UserType.STUDENT;
}
