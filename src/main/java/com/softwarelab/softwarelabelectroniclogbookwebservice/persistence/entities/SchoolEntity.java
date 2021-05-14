package com.softwarelab.softwarelabelectroniclogbookwebservice.persistence.entities;

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
@Table(name = "schools")
public class SchoolEntity extends AbstractBaseEntity<Integer> {
    @Column(nullable = false, unique = true)
    private String name;

    @Column
    private String address;

    @ManyToOne(fetch = FetchType.EAGER,optional = false)
    private StateEntity state;
}
