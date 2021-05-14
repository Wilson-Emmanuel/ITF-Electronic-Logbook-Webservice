package com.softwarelab.softwarelabelectroniclogbookwebservice.persistence.entities;

import com.softwarelab.softwarelabelectroniclogbookwebservice.persistence.enums.RecordStatusConstant;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import javax.persistence.*;
import java.io.Serializable;
import java.time.Instant;
import java.time.LocalDateTime;

@Setter
@Getter
@ToString
@EnableJpaAuditing
@MappedSuperclass
public abstract class AbstractBaseEntity<T extends Serializable> {

    @Id
    @GeneratedValue
    @Column(unique = true)
    private  T id;

    @Version
    private Integer version;//we'll use this for setting when entity is unsaved

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private RecordStatusConstant recordStatus = RecordStatusConstant.ACTIVE;

    @CreatedDate
    @Column(nullable = false)
    private LocalDateTime createdAt;

    @Column
    @LastModifiedDate
    private LocalDateTime modifiedAt;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof AbstractBaseEntity))//this will also check null
            return false;

        AbstractBaseEntity other = (AbstractBaseEntity) o;

        if (id == null) return false;
        return id.equals(other.getId());
    }


    @Override
    public int hashCode() {
        if (id != null) {
            return id.hashCode();
        } else {
            return super.hashCode();
        }
    }

    @PrePersist
    @PreUpdate
    public void prePersist() {
        if (createdAt == null) {
            createdAt = LocalDateTime.now();
            //TODO: set creator too
        }
        modifiedAt = LocalDateTime.now();
    }

}
