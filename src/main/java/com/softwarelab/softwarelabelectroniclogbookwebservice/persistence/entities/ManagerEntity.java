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
@Table(name = "managers")
public class ManagerEntity extends UserEntity<Long> {
    @Column(nullable = false)
    private String companyName;
    
    @Column
    private String companyAddress;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    private StateEntity state;

    @Column
    private String companyType;

    @Column(nullable = false)
    @Builder.Default
    @Enumerated(EnumType.STRING)
    private UserType userType = UserType.MANAGER;
}
