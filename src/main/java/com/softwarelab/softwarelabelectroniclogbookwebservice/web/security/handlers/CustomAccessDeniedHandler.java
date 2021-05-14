package com.softwarelab.softwarelabelectroniclogbookwebservice.web.security.handlers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.softwarelab.softwarelabelectroniclogbookwebservice.services.models.responses.APIResponseJSON;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;

public class CustomAccessDeniedHandler implements AccessDeniedHandler {

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse httpServletResponse, AccessDeniedException ex) throws IOException, ServletException {
        APIResponseJSON<String> apiResponseJSON = new APIResponseJSON<>("Access Denied. "+ex.getMessage());
        httpServletResponse.setStatus(HttpStatus.UNAUTHORIZED.value());
        httpServletResponse.setHeader("Content-Type","application/json");
        httpServletResponse.setCharacterEncoding("UTF-8");
        //System.out.println("Auth failure handler");
        httpServletResponse.getOutputStream().write(new ObjectMapper().writeValueAsString(apiResponseJSON).getBytes());

    }
}
