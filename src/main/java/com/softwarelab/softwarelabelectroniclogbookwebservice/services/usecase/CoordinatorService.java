package com.softwarelab.softwarelabelectroniclogbookwebservice.services.usecase;

import com.softwarelab.softwarelabelectroniclogbookwebservice.persistence.entities.CoordinatorEntity;
import com.softwarelab.softwarelabelectroniclogbookwebservice.services.models.requests.creation.CoordinatorCreationRequest;
import com.softwarelab.softwarelabelectroniclogbookwebservice.services.models.requests.query.CoordinatorQueryRequest;
import com.softwarelab.softwarelabelectroniclogbookwebservice.services.models.requests.update.UserUpdateRequest;
import com.softwarelab.softwarelabelectroniclogbookwebservice.services.models.responses.CoordinatorResponse;
import com.softwarelab.softwarelabelectroniclogbookwebservice.services.models.responses.PagedData;

/**
 * Created by Wilson
 * on Sun, 09/05/2021.
 */
public interface CoordinatorService extends UserService<CoordinatorEntity, CoordinatorResponse, Long>  {
    PagedData<CoordinatorResponse> getCoordinators(CoordinatorQueryRequest coordinatorQueryRequest, int page, int size);
    CoordinatorResponse saveCoordinator(CoordinatorCreationRequest coordinatorCreationRequest);
    CoordinatorResponse updateCoordinator(UserUpdateRequest userUpdateRequest, long coordinatorId);
    CoordinatorResponse updateUserPassword(String password, Long id);
}
