package com.softwarelab.softwarelabelectroniclogbookwebservice;

import com.softwarelab.softwarelabelectroniclogbookwebservice.services.models.requests.creation.SchoolCreationRequest;
import com.softwarelab.softwarelabelectroniclogbookwebservice.services.models.responses.SchoolResponse;
import com.softwarelab.softwarelabelectroniclogbookwebservice.services.models.responses.StateResponse;
import com.softwarelab.softwarelabelectroniclogbookwebservice.services.usecase.SchoolService;
import com.softwarelab.softwarelabelectroniclogbookwebservice.services.usecase.StateService;
import com.softwarelab.softwarelabelectroniclogbookwebservice.services.usecase.impls.GlobalUserService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
class SoftwarelabElectronicLogbookWebserviceApplicationTests {
    private String email = "wilsonemmanuel@gmail.com";
    @Autowired
    GlobalUserService globalUserService;
    @Autowired
    StateService stateService;
    @Autowired
    SchoolService schoolService;

    @Test
    void contextLoads() {

    }

    @Test
    void testStates(){
        List<StateResponse> states =stateService.getAllStates();
        Assertions.assertEquals(states.size(), 38);
        Assertions.assertEquals("Abia State",stateService.getStateById(1).getName());
        Assertions.assertEquals(2,stateService.getStateByName("Adamawa State").getId());

    }

    //@Test
    void testSchoolCreation(){
        SchoolCreationRequest school = SchoolCreationRequest.builder()
                .address("Abakaliki, Ebonyi State")
                .schoolName("Ebonyi State University")
                .stateName("Ebonyi State")
                .build();
        SchoolResponse schoolResponse = schoolService.saveSchool(school);
        Assertions.assertTrue(schoolResponse.getId() > 0);
        Assertions.assertEquals(schoolResponse.getState(),"Ebonyi State");
        SchoolResponse sch = schoolService.getSchoolByName("Ebonyi State University");
        Assertions.assertEquals(sch,schoolResponse);
    }

}
