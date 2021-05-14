package com.softwarelab.softwarelabelectroniclogbookwebservice;

import com.softwarelab.softwarelabelectroniclogbookwebservice.persistence.entities.StateEntity;
import com.softwarelab.softwarelabelectroniclogbookwebservice.persistence.repositories.StateRepository;
import com.softwarelab.softwarelabelectroniclogbookwebservice.services.models.requests.creation.ITFAdminCreationRequest;
import com.softwarelab.softwarelabelectroniclogbookwebservice.services.models.requests.creation.NewUserRequest;
import com.softwarelab.softwarelabelectroniclogbookwebservice.services.usecase.ITFAdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.web.servlet.error.ErrorMvcAutoConfiguration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.web.servlet.handler.SimpleMappingExceptionResolver;

import javax.annotation.PostConstruct;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;

@SpringBootApplication
public class SoftwarelabElectronicLogbookWebserviceApplication {
@Autowired
    private StateRepository stateRepository;
@Autowired
private ITFAdminService adminService;

    public static void main(String[] args) {
        SpringApplication.run(SoftwarelabElectronicLogbookWebserviceApplication.class, args);
    }

    @PostConstruct
    public void autoloader(){
        createStates();
        createDefaultUser();
    }
    private void createDefaultUser(){
        String email = "admin@itf.com";
        if(adminService.userExistsByEmail(email))
            return;
        ITFAdminCreationRequest request = ITFAdminCreationRequest.builder()
                .branch("Abuja Nigeria")
                .staffNo("IFT100037")
                .newUser(NewUserRequest.builder()
                        .email(email)
                        .firstName("Admin")
                        .lastName("Admin")
                        .password("admin1234")
                        .phone("08127367465")
                        .build())
                .build();
        adminService.saveITFAdmin(request);
    }
    private void createStates(){
        if(stateRepository.count() != 38)
            stateRepository.deleteAll();
        else return;

        try {
            File file = new ClassPathResource("states.txt").getFile();
            List<String> files =Files.readAllLines(Paths.get(file.toURI()))
                    .stream().map(fl -> {
                        String[] fs = fl.split("'");
                        return fs[1];
                    }).collect(Collectors.toList());
            List<StateEntity>  states = files.stream().map(st-> StateEntity.builder()
                    .name(st)
                    .build()).collect(Collectors.toList());
            stateRepository.saveAll(states);
        }catch (Exception ex){
            System.out.println(ex.getMessage());
            System.out.println("can't read file");
        }
    }

}
