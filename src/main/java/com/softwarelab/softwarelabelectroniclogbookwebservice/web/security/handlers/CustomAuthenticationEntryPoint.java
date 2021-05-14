package com.softwarelab.softwarelabelectroniclogbookwebservice.web.security.handlers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.softwarelab.softwarelabelectroniclogbookwebservice.services.models.responses.APIResponseJSON;
import org.springframework.context.annotation.Primary;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse httpServletResponse, AuthenticationException ex) throws IOException, ServletException {
        APIResponseJSON<String> apiResponseJSON = new APIResponseJSON<>("Rest Entrypoint error. "+ex.getMessage());
        httpServletResponse.setStatus(HttpStatus.UNAUTHORIZED.value());
        httpServletResponse.setHeader("Content-Type","application/json");
        httpServletResponse.setCharacterEncoding("UTF-8");
        //System.out.println("Auth failure handler");
        httpServletResponse.getOutputStream().write(new ObjectMapper().writeValueAsString(apiResponseJSON).getBytes());
    }
}


