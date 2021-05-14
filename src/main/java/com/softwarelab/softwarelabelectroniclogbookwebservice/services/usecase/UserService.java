package com.softwarelab.softwarelabelectroniclogbookwebservice.services.usecase;

import com.softwarelab.softwarelabelectroniclogbookwebservice.services.models.responses.details.AppUserDetails;

import java.io.Serializable;
import java.util.Optional;

/**
 * Created by Wilson
 * on Sun, 09/05/2021.
 */
public interface UserService<T, M, D extends Serializable> {
    boolean userExistsById(D id);
    boolean userExistsByEmail(String email);
    M getUserByEmail(String email);
    Optional<AppUserDetails> getAppUserDetailsByEmail(String email);
    M getUserById(D id);
    M convertEntityToModel(T entity);
}
