package com.softwarelab.softwarelabelectroniclogbookwebservice.persistence.entities;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

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
@Table(name = "states")
public class StateEntity extends AbstractBaseEntity<Integer> {
    @Column(nullable = false, unique = true)
    private String name;
}
