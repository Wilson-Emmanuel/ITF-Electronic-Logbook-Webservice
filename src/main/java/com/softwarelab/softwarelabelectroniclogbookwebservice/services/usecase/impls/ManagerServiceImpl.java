package com.softwarelab.softwarelabelectroniclogbookwebservice.services.usecase.impls;

import com.softwarelab.softwarelabelectroniclogbookwebservice.persistence.entities.ManagerEntity;
import com.softwarelab.softwarelabelectroniclogbookwebservice.persistence.entities.StateEntity;
import com.softwarelab.softwarelabelectroniclogbookwebservice.persistence.enums.RecordStatusConstant;
import com.softwarelab.softwarelabelectroniclogbookwebservice.persistence.repositories.ManagerRepository;
import com.softwarelab.softwarelabelectroniclogbookwebservice.persistence.repositories.StateRepository;
import com.softwarelab.softwarelabelectroniclogbookwebservice.services.models.dto.ManagerDTO;
import com.softwarelab.softwarelabelectroniclogbookwebservice.services.models.requests.creation.ManagerCreationRequest;
import com.softwarelab.softwarelabelectroniclogbookwebservice.services.models.requests.query.ManagerQueryRequest;
import com.softwarelab.softwarelabelectroniclogbookwebservice.services.models.requests.update.ManagerUpdateRequest;
import com.softwarelab.softwarelabelectroniclogbookwebservice.services.models.responses.ManagerResponse;
import com.softwarelab.softwarelabelectroniclogbookwebservice.services.models.responses.PagedData;
import com.softwarelab.softwarelabelectroniclogbookwebservice.services.models.responses.details.AppUserDetails;
import com.softwarelab.softwarelabelectroniclogbookwebservice.services.usecase.ManagerService;
import com.softwarelab.softwarelabelectroniclogbookwebservice.utilities.PasswordUtil;
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

import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Created by Wilson
 * on Mon, 10/05/2021.
 */
@Service
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ManagerServiceImpl implements ManagerService{

    ManagerRepository managerRepository;
    StateRepository stateRepository;

    GlobalUserService globalUserService;
    PasswordUtil passwordUtil;


    @Override
    public ManagerResponse saveManager(ManagerCreationRequest creationRequest) {
        if(userExistsByEmail(creationRequest.getNewUserRequest().getEmail()))
            throw new ResourceAlreadyExistsException("Manager");

        if(globalUserService.emailExists(creationRequest.getNewUserRequest().getEmail()).isPresent())
            throw new ResourceAlreadyExistsException("Manager");

        StateEntity stateEntity = stateRepository.findByName(creationRequest.getStateName())
                .orElseThrow(()->new ResourceNotFoundException("State"));
        ManagerEntity managerEntity = ManagerEntity.builder()
                .companyAddress(creationRequest.getCompanyAddress())
                .companyName(creationRequest.getCompanyName())
                .companyType(creationRequest.getCompanyType())
                .state(stateEntity)
                .build();
        managerEntity.setEmail(creationRequest.getNewUserRequest().getEmail());
        managerEntity.setFirstName(creationRequest.getNewUserRequest().getFirstName());
        managerEntity.setLastName(creationRequest.getNewUserRequest().getLastName());
        managerEntity.setPhone(creationRequest.getNewUserRequest().getPhone());
        String hash = passwordUtil.getHash(creationRequest.getNewUserRequest().getPassword());
        managerEntity.setPassword(hash);
        return convertEntityToModel(managerRepository.save(managerEntity));
    }

    @Override
    public ManagerResponse updateManager(ManagerUpdateRequest updateRequest, long managerId) {
        ManagerEntity managerEntity = managerRepository.findById(managerId)
                .orElseThrow(() -> new ResourceNotFoundException("Manager"));
        if(userExistsByEmail(updateRequest.getUserUpdateRequest().getEmail()) &&
                !updateRequest.getUserUpdateRequest().getEmail().equals(managerEntity.getEmail()))
            throw new ResourceAlreadyExistsException("Manager email");

        StateEntity stateEntity = stateRepository.findByName(updateRequest.getStateName())
                .orElseThrow(()->new ResourceNotFoundException("State"));

        managerEntity.setPhone(updateRequest.getUserUpdateRequest().getPhone());
        managerEntity.setEmail(updateRequest.getUserUpdateRequest().getEmail());
        managerEntity.setState(stateEntity);
        managerEntity.setCompanyAddress(updateRequest.getCompanyAddress());
        managerEntity.setCompanyName(updateRequest.getCompanyName());
        managerEntity.setCompanyType(updateRequest.getCompanyType());
        return convertEntityToModel(managerRepository.save(managerEntity));
    }



    @Override
    public Optional<AppUserDetails> getAppUserDetailsByEmail(String email) {
        Optional<ManagerEntity> optionalManagerEntity = managerRepository.findByEmail(email);
        return optionalManagerEntity.map(this::getUserDetails);
    }

    @Override
    public ManagerResponse convertEntityToModel(ManagerEntity entity) {
        return ManagerResponse.builder()
                .id(entity.getId())
                .personalDetail(getUserDetails(entity))
                .companyName(entity.getCompanyName())
                .companyAddress(entity.getCompanyAddress())
                .companyState(entity.getState().getName())
                .companyType(entity.getCompanyType())
                .build();
    }

    @Override
    public ManagerResponse updateUserPassword(String password, Long id) {
        ManagerEntity managerEntity = managerRepository.findById(id)
                .orElseThrow(()->new ResourceNotFoundException("Manager"));

        String hash = passwordUtil.getHash(password);
        managerEntity.setPassword(hash);
        return convertEntityToModel(managerRepository.save(managerEntity));
    }


    @Override
    public  boolean userExistsById(Long id){
        return managerRepository.existsById(id);
    }

    @Override
    public boolean userExistsByEmail(String email){
        return managerRepository.existsByEmail(email);
    }

    @Override
    public ManagerResponse getUserByEmail(String email){
        Optional<ManagerEntity> user = managerRepository.findByEmail(email);
        return user.map(this::convertEntityToModel).orElseThrow(()-> new ResourceNotFoundException("User"));
    }

    @Override
    public ManagerResponse getUserById(Long id){
        Optional<ManagerEntity> user = managerRepository.findById(id);
        return user.map(this::convertEntityToModel).orElseThrow(()-> new ResourceNotFoundException("User"));
    }

    public AppUserDetails getUserDetails(ManagerEntity userEntity){
        return AppUserDetails.builder()
                .email(userEntity.getEmail())
                .firstName(userEntity.getFirstName())
                .lastName(userEntity.getLastName())
                .userType(userEntity.getUserType().toString())
                .phone(userEntity.getPhone())
                .build();
    }

    @Override
    public PagedData<ManagerResponse> getManagers(ManagerQueryRequest queryRequest, int page, int size) {
        StateEntity stateEntity = stateRepository.findByName(queryRequest.getStateName()).orElse(null);

        ManagerDTO managerDTO = ManagerDTO.builder()
                .phone(queryRequest.getPhone())
                .email(queryRequest.getEmail())
                .state(stateEntity)
                .build();

        Page<ManagerEntity> managerEntities = getManagers(managerDTO,page,size);
        return new PagedData<>(managerEntities.stream().map(this::convertEntityToModel).collect(Collectors.toList()),
                managerEntities.getTotalElements(),
                managerEntities.getTotalPages());
    }
    private Page<ManagerEntity> getManagers(ManagerDTO managerDTO, int page, int size){
        Pageable pageable = PageRequest.of(page,size, Sort.by("createdAt").descending());

        Specification<ManagerEntity> specification = withActiveStatus(RecordStatusConstant.ACTIVE);
        if(StringUtils.isNotBlank(managerDTO.getEmail())){
            specification = specification.and(withEmail(managerDTO.getEmail()));
        }
        if(StringUtils.isNotBlank(managerDTO.getPhone())){
            specification = specification.and(withPhone(managerDTO.getPhone()));
        }
        if(managerDTO.getState() != null){
            specification = specification.and(withState(managerDTO.getState()));
        }
        return managerRepository.findAll(specification,pageable);
    }
    private static Specification<ManagerEntity> withActiveStatus(RecordStatusConstant status){
        return ((root, criteriaQuery, criteriaBuilder) -> criteriaBuilder.equal(root.get("recordStatus"),status));
    }
    private static Specification<ManagerEntity> withPhone(String phone){
        return ((root, criteriaQuery, criteriaBuilder) -> criteriaBuilder.equal(root.get("phone"),phone));
    }
    private static Specification<ManagerEntity> withEmail(String email){
        return ((root, criteriaQuery, criteriaBuilder) -> criteriaBuilder.equal(root.get("email"),email));
    }
    private static Specification<ManagerEntity> withState(StateEntity state){
        return ((root, criteriaQuery, criteriaBuilder) -> criteriaBuilder.equal(root.get("state"),state.getId()));
    }
}
