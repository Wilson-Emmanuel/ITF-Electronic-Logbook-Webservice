package com.softwarelab.softwarelabelectroniclogbookwebservice.services.usecase;

import com.softwarelab.softwarelabelectroniclogbookwebservice.persistence.entities.ITFAdminEntity;
import com.softwarelab.softwarelabelectroniclogbookwebservice.services.models.requests.creation.ITFAdminCreationRequest;
import com.softwarelab.softwarelabelectroniclogbookwebservice.services.models.requests.update.ITFAdminUpdateRequest;
import com.softwarelab.softwarelabelectroniclogbookwebservice.services.models.responses.ITFAdminResponse;

/**
 * Created by Wilson
 * on Mon, 10/05/2021.
 */
public interface ITFAdminService extends UserService<ITFAdminEntity, ITFAdminResponse, Integer> {
    boolean adminExistsByStaffNo(String staffNo);
    ITFAdminResponse getAdminByStaffNo(String staffNo);
    ITFAdminResponse saveITFAdmin(ITFAdminCreationRequest itfAdminCreationRequest);
    ITFAdminResponse updateITFAdmin(ITFAdminUpdateRequest itfAdminUpdateRequest, int adminId);
    ITFAdminResponse updateUserPassword(String password, Integer id);
}
