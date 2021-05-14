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
@Table(name = "itf_admins")
public class ITFAdminEntity extends UserEntity<Integer> {
    @Column(nullable = false,unique = true)
    private String staffNo;

    @Column(nullable = false)
    private String branch;

    @Column(nullable = false)
    @Builder.Default
    @Enumerated(EnumType.STRING)
    private UserType userType = UserType.ITF;
}
