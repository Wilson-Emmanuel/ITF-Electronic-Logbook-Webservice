package com.softwarelab.softwarelabelectroniclogbookwebservice.services.usecase.impls;

import com.softwarelab.softwarelabelectroniclogbookwebservice.persistence.entities.AuthUserEntity;
import com.softwarelab.softwarelabelectroniclogbookwebservice.persistence.repositories.AuthUserRepository;
import com.softwarelab.softwarelabelectroniclogbookwebservice.services.models.dto.LoggedInUser;
import com.softwarelab.softwarelabelectroniclogbookwebservice.services.models.dto.LoginToken;
import com.softwarelab.softwarelabelectroniclogbookwebservice.services.models.dto.UserAuthDTO;
import com.softwarelab.softwarelabelectroniclogbookwebservice.services.models.enums.UserType;
import com.softwarelab.softwarelabelectroniclogbookwebservice.services.usecase.LoginService;
import com.softwarelab.softwarelabelectroniclogbookwebservice.utilities.PasswordUtil;
import com.softwarelab.softwarelabelectroniclogbookwebservice.web.exceptions.BadRequestException;
import com.softwarelab.softwarelabelectroniclogbookwebservice.web.security.handlers.AuthenticatedUser;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * Created by Wilson
 * on Tue, 11/05/2021.
 */
@Service
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class LoginServiceImpl implements LoginService {

    AuthUserRepository authUserRepository;
    GlobalUserService globalUserService;
    PasswordUtil passwordUtil;


    @Override
    public String saveAuthUser(String email, String userType){
        String token = PasswordUtil.generateToken(email);//using email, random string and time
        AuthUserEntity authUserEntity = AuthUserEntity.builder()
                .userType(UserType.valueOf(userType))
                .email(email)
                .token(token)
                .build();
        authUserRepository.save(authUserEntity);
        return token;
    }

    @Override
    public LoginToken login(String email, String password) {
        //check if user is still loggedin somewhere and delete all
        if(authUserRepository.existsByEmail(email))
            deleteLoginDetails(email);

        //scan all four user tables to get user auth info
        Optional<UserAuthDTO> userAuthDTO = globalUserService.emailExists(email);
        if(!userAuthDTO.isPresent())
            throw new BadRequestException("Wrong email or password");

        //check if password match with the current request
        if(!passwordUtil.isPasswordValid(password,userAuthDTO.get().getHashedPassword()))
            throw new BadRequestException("Wrong email or password");

        //save new login details
        String token = saveAuthUser(email,userAuthDTO.get().getUserType());
        return LoginToken.builder()
                .token(token)
                .build();
    }

    @Override
    public boolean logout(AuthenticatedUser authenticatedUser) {
        deleteLoginDetails(authenticatedUser.getEmail());
        return true;
    }

    @Override
    public Optional<LoggedInUser> getUserDetailsByToken(String token){
        Optional<AuthUserEntity> optionalAuthUserEntity = authUserRepository.findByToken(token);
        if(!optionalAuthUserEntity.isPresent())
            return Optional.empty();

        Optional<UserAuthDTO> userAuthDTO= globalUserService.emailExists(optionalAuthUserEntity.get().getEmail());

        return userAuthDTO.map(userDetails -> LoggedInUser.builder()
                .token(token)
                .email(userDetails.getEmail())
                .loggedIntime(optionalAuthUserEntity.get().getCreatedAt())
                .userType(optionalAuthUserEntity.get().getUserType())
                .build());
    }

    @Override
    public void deleteLoginDetails(String email) {
        authUserRepository.deleteAllByEmail(email);
    }
}
