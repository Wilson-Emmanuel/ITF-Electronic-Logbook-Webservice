package com.softwarelab.softwarelabelectroniclogbookwebservice.persistence.entities;

import com.softwarelab.softwarelabelectroniclogbookwebservice.services.models.enums.UserType;
import lombok.*;

import javax.persistence.*;

/**
 * Created by Wilson
 * on Tue, 11/05/2021.
 */
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "auth_users")
public class AuthUserEntity extends AbstractBaseEntity<Long>{

    @Column(nullable = false, unique = true)
    private String token;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private UserType userType;
}
