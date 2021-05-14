package com.softwarelab.softwarelabelectroniclogbookwebservice.persistence.entities;

import com.softwarelab.softwarelabelectroniclogbookwebservice.services.models.enums.UserType;
import lombok.*;

import javax.persistence.*;

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
@Table(name = "coordinators")
public class CoordinatorEntity extends UserEntity<Long> {
    @Column
    private String departmentName;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    private SchoolEntity school;

    @Column(nullable = false)
    @Builder.Default
    @Enumerated(EnumType.STRING)
    private UserType userType = UserType.COORDINATOR;
}
