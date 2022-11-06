package com.softwarelab.softwarelabelectroniclogbookwebservice.web.security;

import com.softwarelab.softwarelabelectroniclogbookwebservice.services.models.dto.LoggedInUser;
import com.softwarelab.softwarelabelectroniclogbookwebservice.services.usecase.LoginService;
import com.softwarelab.softwarelabelectroniclogbookwebservice.web.properties.ApplicationProperty;
import com.softwarelab.softwarelabelectroniclogbookwebservice.web.security.handlers.AuthenticatedUser;
import com.softwarelab.softwarelabelectroniclogbookwebservice.web.security.handlers.KeyToken;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.AbstractUserDetailsAuthenticationProvider;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

@Component
public class AuthenticationProvider extends AbstractUserDetailsAuthenticationProvider {

    LoginService loginService;
    ApplicationProperty applicationProperty;

    public AuthenticationProvider(LoginService loginService, ApplicationProperty applicationProperty) {
        this.loginService = loginService;
        this.applicationProperty = applicationProperty;
    }

    @Override
    protected void additionalAuthenticationChecks(UserDetails userDetails,
            UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken) throws AuthenticationException {
    }

    @Override
    protected UserDetails retrieveUser(String userName, UsernamePasswordAuthenticationToken authenticationToken)
            throws AuthenticationException {
        KeyToken keyToken = (KeyToken) authenticationToken.getCredentials();

        String token = keyToken.getToken();
        String clientKey = keyToken.getKey();

        if (!clientKey.equals(applicationProperty.getApiRestKey())) {
            throw new BadCredentialsException("Invalid API key.");
        }
        LoggedInUser loggedInUser = loginService.getUserDetailsByToken(token)
                .orElseThrow(() -> new UsernameNotFoundException("Invalid token"));

        long timeDiff = loggedInUser.getLoggedIntime().until(LocalDateTime.now(), ChronoUnit.MINUTES);
        if (timeDiff > applicationProperty.getMaxTokenLifeInMinutes()) {
            loginService.deleteLoginDetails(loggedInUser.getEmail());
            throw new UsernameNotFoundException("Session expired");
        }

        AuthenticatedUser authenticatedUser = new AuthenticatedUser();
        authenticatedUser.addAuthority(loggedInUser.getUserType().toString());
        authenticatedUser.setUserType(loggedInUser.getUserType());
        authenticatedUser.setEmail(loggedInUser.getEmail());
        return authenticatedUser;
    }

}
