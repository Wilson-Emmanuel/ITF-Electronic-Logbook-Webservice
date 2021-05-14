package com.softwarelab.softwarelabelectroniclogbookwebservice.services.usecase;

import com.softwarelab.softwarelabelectroniclogbookwebservice.services.models.responses.StateResponse;

import java.util.List;

/**
 * Created by Wilson
 * on Sun, 09/05/2021.
 */
public interface StateService {
    boolean stateExistsByName(String name);
    boolean stateExistsById(Integer stateId);
    StateResponse getStateByName(String name);
    StateResponse getStateById(Integer id);
    List<StateResponse> getAllStates();
    //StateResponse saveState(StateCreationRequest stateCreationRequest);//all states already autoloaded
}
