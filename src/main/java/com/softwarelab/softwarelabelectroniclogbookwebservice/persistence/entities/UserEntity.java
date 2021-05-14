package com.softwarelab.softwarelabelectroniclogbookwebservice.persistence.entities;

import com.softwarelab.softwarelabelectroniclogbookwebservice.services.models.enums.UserType;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.MappedSuperclass;
import java.io.Serializable;

/**
 * Created by Wilson
 * on Fri, 07/05/2021.
 */
@Setter
@Getter
@ToString
@MappedSuperclass
public abstract class UserEntity<T extends Serializable> extends AbstractBaseEntity<T>{
    @Column(nullable = false)
    private String firstName;

    @Column(nullable = false)
    private String lastName;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    private String phone;

    @Column(nullable = false)
    private String password;
}
