package com.softwarelab.softwarelabelectroniclogbookwebservice.web.security;

import com.softwarelab.softwarelabelectroniclogbookwebservice.web.security.handlers.KeyToken;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.RequestMatcher;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;

public class AuthenticationFilter extends AbstractAuthenticationProcessingFilter {

    private static final String CLIENT_REQUEST_KEY = "x-client-request-key";
    AuthenticationFilter(final RequestMatcher requiresAuth) {
        super(requiresAuth);
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws AuthenticationException, IOException, ServletException {

            Optional<String> optionalToken = Optional.ofNullable(httpServletRequest.getHeader(AUTHORIZATION));
            Optional<String> optionalRequestKey = Optional.ofNullable(httpServletRequest.getHeader(CLIENT_REQUEST_KEY));
            if(!optionalToken.isPresent() || !optionalToken.get().toLowerCase().trim().startsWith("bearer ")){
                throw new BadCredentialsException("Bad request. URI:"+httpServletRequest.getRequestURI());
            }
            if(!optionalRequestKey.isPresent()){
                throw new BadCredentialsException("Bad request. URI:"+httpServletRequest.getRequestURI());
            }
            String token = optionalToken.get().trim().substring(7).trim();//removes "bearer "
            String clientRequestKey = optionalRequestKey.get().trim();

            KeyToken keyToken = KeyToken.builder().key(clientRequestKey).token(token).build();
            Authentication requestAuthentication = new UsernamePasswordAuthenticationToken(authenticationDetailsSource.buildDetails(httpServletRequest), keyToken);
            return getAuthenticationManager().authenticate(requestAuthentication);
    }

    @Override
    protected void successfulAuthentication(final HttpServletRequest request, final HttpServletResponse response, final FilterChain chain, final Authentication authResult) throws IOException, ServletException {
        SecurityContextHolder.getContext().setAuthentication(authResult);
        chain.doFilter(request, response);
    }

    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) throws IOException, ServletException {
        super.unsuccessfulAuthentication(request, response, failed);
    }
}
