package com.softwarelab.softwarelabelectroniclogbookwebservice.persistence.repositories;

import com.softwarelab.softwarelabelectroniclogbookwebservice.persistence.entities.AuthUserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.Optional;

/**
 * Created by Wilson
 * on Tue, 11/05/2021.
 */
@Repository
public interface AuthUserRepository extends JpaRepository<AuthUserEntity,Long> {
    boolean existsByEmail(String email);
    boolean existsByToken(String token);
    @Transactional
    void deleteAllByEmail(String email);
    Optional<AuthUserEntity> findByEmail(String email);
    Optional<AuthUserEntity> findByToken(String token);
}
