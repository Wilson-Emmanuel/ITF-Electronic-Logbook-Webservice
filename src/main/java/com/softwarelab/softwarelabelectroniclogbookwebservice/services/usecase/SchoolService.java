package com.softwarelab.softwarelabelectroniclogbookwebservice.services.usecase;

import com.softwarelab.softwarelabelectroniclogbookwebservice.persistence.entities.SchoolEntity;
import com.softwarelab.softwarelabelectroniclogbookwebservice.services.models.requests.creation.SchoolCreationRequest;
import com.softwarelab.softwarelabelectroniclogbookwebservice.services.models.requests.update.SchoolUpdateRequest;
import com.softwarelab.softwarelabelectroniclogbookwebservice.services.models.requests.query.SchoolQueryRequest;
import com.softwarelab.softwarelabelectroniclogbookwebservice.services.models.responses.PagedData;
import com.softwarelab.softwarelabelectroniclogbookwebservice.services.models.responses.SchoolResponse;

/**
 * Created by Wilson
 * on Sun, 09/05/2021.
 */
public interface SchoolService {
    boolean schoolExistsByName(String schoolName);
    boolean schoolExistsById(Integer schoolId);
    SchoolResponse getSchoolByName(String schoolName);
    SchoolResponse getSchoolById(Integer schoolId);
    PagedData<SchoolResponse> getSchools(SchoolQueryRequest schoolQueryRequest, int page, int size);
    SchoolResponse saveSchool(SchoolCreationRequest schoolCreationRequest);
    SchoolResponse updateSchool(SchoolUpdateRequest schoolUpdateRequest, Integer schoolId);
    SchoolResponse convertSchoolEntityToModel(SchoolEntity schoolEntity);
}
