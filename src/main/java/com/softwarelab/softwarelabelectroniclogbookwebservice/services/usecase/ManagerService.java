package com.softwarelab.softwarelabelectroniclogbookwebservice.services.usecase;

import com.softwarelab.softwarelabelectroniclogbookwebservice.persistence.entities.ManagerEntity;
import com.softwarelab.softwarelabelectroniclogbookwebservice.services.models.requests.creation.ManagerCreationRequest;
import com.softwarelab.softwarelabelectroniclogbookwebservice.services.models.requests.query.ManagerQueryRequest;
import com.softwarelab.softwarelabelectroniclogbookwebservice.services.models.requests.update.ManagerUpdateRequest;
import com.softwarelab.softwarelabelectroniclogbookwebservice.services.models.responses.ManagerResponse;
import com.softwarelab.softwarelabelectroniclogbookwebservice.services.models.responses.PagedData;

/**
 * Created by Wilson
 * on Mon, 10/05/2021.
 */
public interface ManagerService extends UserService<ManagerEntity, ManagerResponse, Long>   {
    PagedData<ManagerResponse> getManagers(ManagerQueryRequest queryRequest, int page, int size);
    ManagerResponse saveManager(ManagerCreationRequest creationRequest);
    ManagerResponse updateManager(ManagerUpdateRequest updateRequest, long managerId);
    ManagerResponse updateUserPassword(String password, Long id);
}
