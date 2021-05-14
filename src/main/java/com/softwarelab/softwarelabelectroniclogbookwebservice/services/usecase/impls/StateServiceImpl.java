package com.softwarelab.softwarelabelectroniclogbookwebservice.services.usecase.impls;

import com.softwarelab.softwarelabelectroniclogbookwebservice.persistence.entities.StateEntity;
import com.softwarelab.softwarelabelectroniclogbookwebservice.persistence.repositories.StateRepository;
import com.softwarelab.softwarelabelectroniclogbookwebservice.services.usecase.StateService;
import com.softwarelab.softwarelabelectroniclogbookwebservice.services.models.responses.StateResponse;
import com.softwarelab.softwarelabelectroniclogbookwebservice.web.exceptions.ResourceNotFoundException;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by Wilson
 * on Sun, 09/05/2021.
 */
@Service
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class StateServiceImpl implements StateService {

    StateRepository stateRepository;

    @Override
    public boolean stateExistsByName(String name) {
        return stateRepository.existsByName(name);
    }

    @Override
    public boolean stateExistsById(Integer stateId) {
        return stateRepository.existsById(stateId);
    }

    @Override
    public StateResponse getStateByName(String name) {
        return stateRepository.findByName(name).map(this::convertStateEntityToModel)
                .orElseThrow(() -> new ResourceNotFoundException("State"));
    }

    @Override
    public StateResponse getStateById(Integer id) {
        return stateRepository.findById(id).map(this::convertStateEntityToModel)
                .orElseThrow(() -> new ResourceNotFoundException("State"));
    }

    @Override
    public List<StateResponse> getAllStates() {
        return stateRepository.findAll().stream().map(this::convertStateEntityToModel)
                .collect(Collectors.toList());
    }

    private StateResponse convertStateEntityToModel(StateEntity stateEntity){
        return StateResponse.builder()
                .id(stateEntity.getId())
                .name(stateEntity.getName())
                .build();
    }
}
