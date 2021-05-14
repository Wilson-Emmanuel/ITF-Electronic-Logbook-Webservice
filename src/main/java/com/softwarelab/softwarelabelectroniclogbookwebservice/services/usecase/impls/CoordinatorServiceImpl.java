package com.softwarelab.softwarelabelectroniclogbookwebservice.services.usecase.impls;

import com.softwarelab.softwarelabelectroniclogbookwebservice.persistence.entities.CoordinatorEntity;
import com.softwarelab.softwarelabelectroniclogbookwebservice.persistence.entities.SchoolEntity;
import com.softwarelab.softwarelabelectroniclogbookwebservice.persistence.entities.StateEntity;
import com.softwarelab.softwarelabelectroniclogbookwebservice.persistence.enums.RecordStatusConstant;
import com.softwarelab.softwarelabelectroniclogbookwebservice.persistence.repositories.CoordinatorRepository;
import com.softwarelab.softwarelabelectroniclogbookwebservice.persistence.repositories.SchoolRepository;
import com.softwarelab.softwarelabelectroniclogbookwebservice.persistence.repositories.StateRepository;
import com.softwarelab.softwarelabelectroniclogbookwebservice.services.models.dto.CoordinatorDTO;
import com.softwarelab.softwarelabelectroniclogbookwebservice.services.models.requests.creation.CoordinatorCreationRequest;
import com.softwarelab.softwarelabelectroniclogbookwebservice.services.models.requests.query.CoordinatorQueryRequest;
import com.softwarelab.softwarelabelectroniclogbookwebservice.services.models.requests.update.UserUpdateRequest;
import com.softwarelab.softwarelabelectroniclogbookwebservice.services.models.responses.CoordinatorResponse;
import com.softwarelab.softwarelabelectroniclogbookwebservice.services.models.responses.PagedData;
import com.softwarelab.softwarelabelectroniclogbookwebservice.services.models.responses.details.AppUserDetails;
import com.softwarelab.softwarelabelectroniclogbookwebservice.services.usecase.CoordinatorService;
import com.softwarelab.softwarelabelectroniclogbookwebservice.services.usecase.SchoolService;
import com.softwarelab.softwarelabelectroniclogbookwebservice.utilities.PasswordUtil;
import com.softwarelab.softwarelabelectroniclogbookwebservice.web.exceptions.ResourceAlreadyExistsException;
import com.softwarelab.softwarelabelectroniclogbookwebservice.web.exceptions.ResourceNotFoundException;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.Join;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Created by Wilson
 * on Sun, 09/05/2021.
 */
@Service
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class CoordinatorServiceImpl implements CoordinatorService {

    SchoolRepository schoolRepository;
    CoordinatorRepository coordinatorRepository;
    SchoolService schoolService;
    StateRepository stateRepository;
    GlobalUserService globalUserService;
    PasswordUtil passwordUtil;
    public CoordinatorServiceImpl(SchoolService schoolService, StateRepository stateRepository, SchoolRepository schoolRepository, CoordinatorRepository coordinatorRepository, GlobalUserService globalUserService, PasswordUtil passwordUtil) {
        this.schoolService = schoolService;
        this.schoolRepository = schoolRepository;
        this.coordinatorRepository = coordinatorRepository;
        this.stateRepository = stateRepository;
        this.globalUserService = globalUserService;
        this.passwordUtil = passwordUtil;
    }

    @Override
    public Optional<AppUserDetails> getAppUserDetailsByEmail(String email) {
        Optional<CoordinatorEntity> optionalCoordinatorEntity = coordinatorRepository.findByEmail(email);
        return optionalCoordinatorEntity.map(this::getUserDetails);
    }

    @Override
    public CoordinatorResponse convertEntityToModel(CoordinatorEntity entity) {
        return CoordinatorResponse.builder()
                .id(entity.getId())
                .school(schoolService.convertSchoolEntityToModel(entity.getSchool()))
                .department(entity.getDepartmentName())
                .appUserDetails(getUserDetails(entity))
                .build();
    }

    @Override
    public CoordinatorResponse updateUserPassword(String password, Long id) {
        CoordinatorEntity coordinatorEntity = coordinatorRepository.findById(id)
                .orElseThrow(()->new ResourceNotFoundException("Coordinator"));

        String hash = passwordUtil.getHash(password);
        coordinatorEntity.setPassword(hash);
        return convertEntityToModel(coordinatorRepository.save(coordinatorEntity));
    }



    @Override
    public  boolean userExistsById(Long id){
        return coordinatorRepository.existsById(id);
    }

    @Override
    public boolean userExistsByEmail(String email){
        return coordinatorRepository.existsByEmail(email);
    }

    @Override
    public CoordinatorResponse getUserByEmail(String email){
        Optional<CoordinatorEntity> user = coordinatorRepository.findByEmail(email);
        return user.map(this::convertEntityToModel).orElseThrow(()-> new ResourceNotFoundException("User"));
    }

    @Override
    public CoordinatorResponse getUserById(Long id){
        Optional<CoordinatorEntity> user = coordinatorRepository.findById(id);
        return user.map(this::convertEntityToModel).orElseThrow(()-> new ResourceNotFoundException("User"));
    }

    public AppUserDetails getUserDetails(CoordinatorEntity userEntity){
        return AppUserDetails.builder()
                .email(userEntity.getEmail())
                .firstName(userEntity.getFirstName())
                .lastName(userEntity.getLastName())
                .phone(userEntity.getPhone())
                .userType(userEntity.getUserType().toString())
                .build();
    }

    @Override
    public PagedData<CoordinatorResponse> getCoordinators(CoordinatorQueryRequest coordinatorQueryRequest, int page, int size) {
        SchoolEntity schoolEntity = schoolRepository.findByName(coordinatorQueryRequest.getSchoolName()).orElse(null);
        StateEntity stateEntity = stateRepository.findByName(coordinatorQueryRequest.getState()).orElse(null);
        CoordinatorDTO coordinatorDTO = CoordinatorDTO.builder()
                .email(coordinatorQueryRequest.getEmail())
                .phone(coordinatorQueryRequest.getPhone())
                .state(stateEntity)
                .school(schoolEntity)
                .build();
        Page<CoordinatorEntity> coordinatorEntityPage = getCoordinators(coordinatorDTO,page,size);
        return new PagedData<>(coordinatorEntityPage.stream().map(this::convertEntityToModel).collect(Collectors.toList()),
                coordinatorEntityPage.getTotalElements(),
                coordinatorEntityPage.getTotalPages());
    }

    @Override
    public CoordinatorResponse saveCoordinator(CoordinatorCreationRequest coordinatorCreationRequest) {
        SchoolEntity schoolEntity = schoolRepository.findByName(coordinatorCreationRequest.getSchoolName())
                .orElseThrow(() -> new ResourceNotFoundException("School"));

        if(coordinatorRepository.existsByEmail(coordinatorCreationRequest.getNewUser().getEmail()))
            throw new ResourceAlreadyExistsException("Coordinator");

        if(globalUserService.emailExists(coordinatorCreationRequest.getNewUser().getEmail()).isPresent())
            throw new ResourceAlreadyExistsException("Coordinator");

        CoordinatorEntity coordinatorEntity = CoordinatorEntity.builder()
                .departmentName(coordinatorCreationRequest.getDepartment())
                .school(schoolEntity)
                .build();
        coordinatorEntity.setEmail(coordinatorCreationRequest.getNewUser().getEmail());
        coordinatorEntity.setFirstName(coordinatorCreationRequest.getNewUser().getFirstName());
        coordinatorEntity.setLastName(coordinatorCreationRequest.getNewUser().getLastName());
        coordinatorEntity.setPhone(coordinatorCreationRequest.getNewUser().getPhone());
        String hash = passwordUtil.getHash(coordinatorCreationRequest.getNewUser().getPassword());
        coordinatorEntity.setPassword(hash);
        return convertEntityToModel(coordinatorRepository.save(coordinatorEntity));
    }

    @Override
    public CoordinatorResponse updateCoordinator(UserUpdateRequest userUpdateRequest, long coordinatorId) {
        CoordinatorEntity coordinatorEntity = coordinatorRepository.findById(coordinatorId)
                .orElseThrow(() -> new ResourceNotFoundException("Coordinator"));

        if(coordinatorRepository.existsByEmail(userUpdateRequest.getEmail()) &&
                !userUpdateRequest.getEmail().equals(coordinatorEntity.getEmail()))
            throw new ResourceAlreadyExistsException("Coordinator email");

        coordinatorEntity.setPhone(userUpdateRequest.getPhone());
        coordinatorEntity.setEmail(userUpdateRequest.getEmail());

        return convertEntityToModel(coordinatorRepository.save(coordinatorEntity));
    }

    private Page<CoordinatorEntity> getCoordinators(CoordinatorDTO coordinatorDTO, int page, int size) {
        Pageable pageable = PageRequest.of(page,size, Sort.by("createdAt").descending());

        Specification<CoordinatorEntity> specification = withActiveStatus(RecordStatusConstant.ACTIVE);
        if(StringUtils.isNotBlank(coordinatorDTO.getEmail())){
            specification = specification.and(withEmail(coordinatorDTO.getEmail()));
        }
        if(StringUtils.isNotBlank(coordinatorDTO.getPhone())){
            specification = specification.and(withPhone(coordinatorDTO.getPhone()));
        }
        if(coordinatorDTO.getSchool() != null){
            specification = specification.and(withSchool(coordinatorDTO.getSchool()));
        }
        if(coordinatorDTO.getState() != null){
            specification = specification.and(withState(coordinatorDTO.getState()));
        }
        return coordinatorRepository.findAll(specification,pageable);
    }

    private static Specification<CoordinatorEntity> withActiveStatus(RecordStatusConstant status){
        return ((root, criteriaQuery, criteriaBuilder) -> criteriaBuilder.equal(root.get("recordStatus"),status));
    }
    private static Specification<CoordinatorEntity> withEmail(String email){
        return ((root, criteriaQuery, criteriaBuilder) -> criteriaBuilder.equal(root.get("email"),email));
    }
    private static Specification<CoordinatorEntity> withPhone(String phone){
        return ((root, criteriaQuery, criteriaBuilder) -> criteriaBuilder.equal(root.get("phone"),phone));
    }
    private static Specification<CoordinatorEntity> withSchool(SchoolEntity school){
        return ((root, criteriaQuery, criteriaBuilder) -> criteriaBuilder.equal(root.get("school"),school.getId()));
    }
    private static Specification<CoordinatorEntity> withState(StateEntity state){
        return ((root, criteriaQuery, criteriaBuilder) -> {
            Join<CoordinatorEntity,SchoolEntity> schoolSpec = root.join("school");
            return criteriaBuilder.equal(schoolSpec.get("state"),state.getId());
        });
    }
}
