package com.softwarelab.softwarelabelectroniclogbookwebservice.services.usecase.impls;

import com.softwarelab.softwarelabelectroniclogbookwebservice.persistence.entities.SchoolEntity;
import com.softwarelab.softwarelabelectroniclogbookwebservice.persistence.entities.StateEntity;
import com.softwarelab.softwarelabelectroniclogbookwebservice.persistence.enums.RecordStatusConstant;
import com.softwarelab.softwarelabelectroniclogbookwebservice.persistence.repositories.SchoolRepository;
import com.softwarelab.softwarelabelectroniclogbookwebservice.persistence.repositories.StateRepository;
import com.softwarelab.softwarelabelectroniclogbookwebservice.services.models.dto.SchoolDTO;
import com.softwarelab.softwarelabelectroniclogbookwebservice.services.models.requests.creation.SchoolCreationRequest;
import com.softwarelab.softwarelabelectroniclogbookwebservice.services.models.requests.query.SchoolQueryRequest;
import com.softwarelab.softwarelabelectroniclogbookwebservice.services.models.requests.update.SchoolUpdateRequest;
import com.softwarelab.softwarelabelectroniclogbookwebservice.services.models.responses.PagedData;
import com.softwarelab.softwarelabelectroniclogbookwebservice.services.models.responses.SchoolResponse;
import com.softwarelab.softwarelabelectroniclogbookwebservice.services.usecase.SchoolService;
import com.softwarelab.softwarelabelectroniclogbookwebservice.web.exceptions.ResourceAlreadyExistsException;
import com.softwarelab.softwarelabelectroniclogbookwebservice.web.exceptions.ResourceNotFoundException;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

/**
 * Created by Wilson
 * on Sun, 09/05/2021.
 */
@Service
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class SchoolServiceImpl implements SchoolService {
    SchoolRepository schoolRepository;
    StateRepository stateRepository;


    @Override
    public boolean schoolExistsByName(String schoolName) {
        return schoolRepository.existsByName(schoolName);
    }

    @Override
    public boolean schoolExistsById(Integer schoolId) {
        return schoolRepository.existsById(schoolId);
    }

    @Override
    public SchoolResponse getSchoolByName(String schoolName) {
        return schoolRepository.findByName(schoolName).map(this::convertSchoolEntityToModel)
                .orElseThrow(() -> new ResourceNotFoundException("School"));
    }

    @Override
    public SchoolResponse getSchoolById(Integer schoolId) {
        return schoolRepository.findById(schoolId).map(this::convertSchoolEntityToModel)
                .orElseThrow(() -> new ResourceNotFoundException("School"));
    }

    @Override
public PagedData<SchoolResponse> getSchools(SchoolQueryRequest schoolQueryRequest, int page, int size){
        StateEntity stateEntity = stateRepository.findByName(schoolQueryRequest.getStateName()).orElse(null);

        SchoolDTO schoolDTO = SchoolDTO.builder()
                .schoolName(schoolQueryRequest.getSchoolName())
                .state(stateEntity)
                .build();
    Page<SchoolEntity> schoolEntityPage = getSchools(schoolDTO,page,size);
    return new PagedData<>(schoolEntityPage.stream().map(this::convertSchoolEntityToModel).collect(Collectors.toList()),
            schoolEntityPage.getTotalElements(),
            schoolEntityPage.getTotalPages());
}
    private Page<SchoolEntity> getSchools(SchoolDTO schoolDTO, int page, int size) {
        Pageable pageable = PageRequest.of(page,size, Sort.by("createdAt").descending());
        Specification<SchoolEntity> specification = withActiveStatus(RecordStatusConstant.ACTIVE);
        if(StringUtils.isNotBlank(schoolDTO.getSchoolName())){
            specification = specification.and(withName(schoolDTO.getSchoolName()));
        }
        if(schoolDTO.getState() != null){
            specification = specification.and(withState(schoolDTO.getState()));
        }
        return schoolRepository.findAll(specification,pageable);
    }

    @Override
    public SchoolResponse saveSchool(SchoolCreationRequest schoolCreationRequest) {
        StateEntity stateEntity = stateRepository.findByName(schoolCreationRequest.getStateName())
                .orElseThrow(() -> new ResourceNotFoundException("State"));

        if(schoolRepository.existsByName(schoolCreationRequest.getSchoolName()))
            throw new ResourceAlreadyExistsException("School");

        SchoolEntity schoolEntity = SchoolEntity.builder()
                .name(schoolCreationRequest.getSchoolName())
                .address(schoolCreationRequest.getAddress())
                .state(stateEntity)
                .build();
        return convertSchoolEntityToModel(schoolRepository.save(schoolEntity));
    }

    @Override
    public SchoolResponse updateSchool(SchoolUpdateRequest updateRequest, Integer schoolId) {
        SchoolEntity schoolEntity = schoolRepository.findById(schoolId)
                .orElseThrow(()->new ResourceNotFoundException("School"));

        if(schoolRepository.existsByName(updateRequest.getSchoolName()) &&
                !updateRequest.getSchoolName().equals(schoolEntity.getName()))
            throw new ResourceAlreadyExistsException("School Name");//another school already has the name

        StateEntity stateEntity = stateRepository.findByName(updateRequest.getStateName())
                .orElseThrow(() -> new ResourceNotFoundException("State"));

        schoolEntity.setAddress(updateRequest.getAddress());
        schoolEntity.setState(stateEntity);
        schoolEntity.setName(updateRequest.getSchoolName());
        return convertSchoolEntityToModel(schoolRepository.save(schoolEntity));
    }

    @Override
public SchoolResponse convertSchoolEntityToModel(SchoolEntity schoolEntity){
        return SchoolResponse.builder()
                .id(schoolEntity.getId())
                .name(schoolEntity.getName())
                .state(schoolEntity.getState().getName())//eagerly loaded
                .address(schoolEntity.getAddress())
                .build();
    }
    private static Specification<SchoolEntity> withActiveStatus(RecordStatusConstant status){
        return ((root, criteriaQuery, criteriaBuilder) -> criteriaBuilder.equal(root.get("recordStatus"),status));
    }
    private static Specification<SchoolEntity> withName(String name){
        return (root, criteriaQuery, criteriaBuilder) -> criteriaBuilder.equal(root.get("name"),name);
    }
    private static Specification<SchoolEntity> withState(StateEntity state){
        return ((root, criteriaQuery, criteriaBuilder) -> criteriaBuilder.equal(root.get("state"),state.getId()));
    }
}
