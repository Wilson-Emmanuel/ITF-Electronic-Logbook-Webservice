package com.softwarelab.softwarelabelectroniclogbookwebservice.services.usecase.impls;

import com.softwarelab.softwarelabelectroniclogbookwebservice.persistence.entities.ITFAdminEntity;
import com.softwarelab.softwarelabelectroniclogbookwebservice.persistence.repositories.ITFAdminRepository;
import com.softwarelab.softwarelabelectroniclogbookwebservice.services.models.requests.creation.ITFAdminCreationRequest;
import com.softwarelab.softwarelabelectroniclogbookwebservice.services.models.requests.update.ITFAdminUpdateRequest;
import com.softwarelab.softwarelabelectroniclogbookwebservice.services.models.responses.ITFAdminResponse;
import com.softwarelab.softwarelabelectroniclogbookwebservice.services.models.responses.details.AppUserDetails;
import com.softwarelab.softwarelabelectroniclogbookwebservice.services.usecase.ITFAdminService;
import com.softwarelab.softwarelabelectroniclogbookwebservice.utilities.PasswordUtil;
import com.softwarelab.softwarelabelectroniclogbookwebservice.web.exceptions.ResourceAlreadyExistsException;
import com.softwarelab.softwarelabelectroniclogbookwebservice.web.exceptions.ResourceNotFoundException;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * Created by Wilson
 * on Mon, 10/05/2021.
 */
@Service
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE,makeFinal = true)
public class ITFAdminServiceImpl implements ITFAdminService{

    ITFAdminRepository itfAdminRepository;
    GlobalUserService globalUserService;
    PasswordUtil passwordUtil;


    @Override
    public boolean adminExistsByStaffNo(String staffNo) {
        return itfAdminRepository.existsByStaffNo(staffNo);
    }

    @Override
    public ITFAdminResponse getAdminByStaffNo(String staffNo) {
        ITFAdminEntity adminEntity = itfAdminRepository.findByStaffNo(staffNo)
                .orElseThrow(()-> new ResourceNotFoundException("Admin"));
        return convertEntityToModel(adminEntity);
    }

    @Override
    public ITFAdminResponse saveITFAdmin(ITFAdminCreationRequest creationRequest) {

        if(globalUserService.emailExists(creationRequest.getNewUser().getEmail()).isPresent())
            throw new ResourceAlreadyExistsException("Staff");


        if(itfAdminRepository.existsByStaffNoOrEmail(creationRequest.getStaffNo(), creationRequest.getNewUser().getEmail()))
            throw new ResourceAlreadyExistsException("Staff or Email");

        ITFAdminEntity adminEntity = ITFAdminEntity.builder()
                .branch(creationRequest.getBranch())
                .staffNo(creationRequest.getStaffNo())
                .build();
        adminEntity.setEmail(creationRequest.getNewUser().getEmail());
        adminEntity.setPhone(creationRequest.getNewUser().getPhone());
        adminEntity.setFirstName(creationRequest.getNewUser().getFirstName());
        adminEntity.setLastName(creationRequest.getNewUser().getLastName());
        String hash = passwordUtil.getHash(creationRequest.getNewUser().getPassword());
        adminEntity.setPassword(hash);
        return convertEntityToModel(itfAdminRepository.save(adminEntity));
    }

    @Override
    public ITFAdminResponse updateITFAdmin(ITFAdminUpdateRequest updateRequest, int adminId) {
        ITFAdminEntity adminEntity = itfAdminRepository.findById(adminId)
                .orElseThrow(()->new ResourceNotFoundException("Admin"));

        if(userExistsByEmail(updateRequest.getUpdateRequest().getEmail()) &&
            !updateRequest.getUpdateRequest().getEmail().equals(adminEntity.getEmail()))
            throw new ResourceAlreadyExistsException("Admin");

        if(adminExistsByStaffNo(updateRequest.getStaffNo()) &&
                !updateRequest.getStaffNo().equals(adminEntity.getStaffNo()))
            throw new ResourceAlreadyExistsException("Admin");

        adminEntity.setBranch(updateRequest.getBranch());
        adminEntity.setStaffNo(updateRequest.getStaffNo());
        adminEntity.setEmail(updateRequest.getUpdateRequest().getEmail());
        adminEntity.setPhone(updateRequest.getUpdateRequest().getPhone());

        return convertEntityToModel(itfAdminRepository.save(adminEntity));
    }

    @Override
    public Optional<AppUserDetails> getAppUserDetailsByEmail(String email) {
        Optional<ITFAdminEntity> optionalITFAdminEntity = itfAdminRepository.findByEmail(email);
        return optionalITFAdminEntity.map(this::getUserDetails);
    }

    @Override
    public ITFAdminResponse convertEntityToModel(ITFAdminEntity entity) {
        return ITFAdminResponse.builder()
                .branch(entity.getBranch())
                .id(entity.getId())
                .personalDetail(getUserDetails(entity))
                .staffNo(entity.getStaffNo())
                .build();
    }

    @Override
    public  boolean userExistsById(Integer id){
        return itfAdminRepository.existsById(id);
    }

    @Override
    public boolean userExistsByEmail(String email){
        return itfAdminRepository.existsByEmail(email);
    }

    @Override
    public ITFAdminResponse getUserByEmail(String email){
        Optional<ITFAdminEntity> user = itfAdminRepository.findByEmail(email);
        return user.map(this::convertEntityToModel).orElseThrow(()-> new ResourceNotFoundException("User"));
    }

    @Override
    public ITFAdminResponse getUserById(Integer id){
        Optional<ITFAdminEntity> user = itfAdminRepository.findById(id);
        return user.map(this::convertEntityToModel).orElseThrow(()-> new ResourceNotFoundException("User"));
    }

    public AppUserDetails getUserDetails(ITFAdminEntity userEntity){
        return AppUserDetails.builder()
                .email(userEntity.getEmail())
                .firstName(userEntity.getFirstName())
                .lastName(userEntity.getLastName())
                .userType(userEntity.getUserType().toString())
                .phone(userEntity.getPhone())
                .build();
    }

    @Override
    public ITFAdminResponse updateUserPassword(String password, Integer id) {
        ITFAdminEntity adminEntity = itfAdminRepository.findById(id)
                .orElseThrow(()->new ResourceNotFoundException("Admin"));

        String hash = passwordUtil.getHash(password);
        adminEntity.setPassword(hash);
        return convertEntityToModel(itfAdminRepository.save(adminEntity));
    }


}
