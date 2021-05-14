package com.softwarelab.softwarelabelectroniclogbookwebservice.web.security;

import com.softwarelab.softwarelabelectroniclogbookwebservice.web.security.handlers.CustomAccessDeniedHandler;
import com.softwarelab.softwarelabelectroniclogbookwebservice.web.security.handlers.CustomAuthenticationEntryPoint;
import com.softwarelab.softwarelabelectroniclogbookwebservice.web.security.handlers.CustomAuthenticationFailureHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.AnonymousAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.NegatedRequestMatcher;
import org.springframework.security.web.util.matcher.OrRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(
        prePostEnabled = true,
        securedEnabled = true,
        jsr250Enabled = true)
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {


    private static final RequestMatcher PUBLIC_URLS = new OrRequestMatcher(
            new AntPathRequestMatcher("/v1/auth/login"),
            new AntPathRequestMatcher("/swagger-resources/**"),
            new AntPathRequestMatcher("/swagger-ui/**"),
            new AntPathRequestMatcher("/swagger-ui.html"),
            new AntPathRequestMatcher("/v2/api-docs"),
            new AntPathRequestMatcher("/error"),
            new AntPathRequestMatcher("/h2-console/**")
    );
    private static final RequestMatcher PROTECTED_URLS = new NegatedRequestMatcher(PUBLIC_URLS);
    AuthenticationProvider provider;

    public SecurityConfiguration(AuthenticationProvider authenticationProvider) {
        super();
        this.provider=authenticationProvider;
    }

    @Override
    protected void configure(final AuthenticationManagerBuilder auth) {
        auth.authenticationProvider(provider);
    }


    @Override
    public void configure(WebSecurity webSecurity) {
        webSecurity.ignoring().requestMatchers(PUBLIC_URLS);//url that will be ignored
    }


   @Override
    public void configure(HttpSecurity http) throws Exception {
        http

                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .exceptionHandling().accessDeniedHandler(accessDeniedHandler())
               .authenticationEntryPoint(authenticationEntryPoint())
                .and()
                .authenticationProvider(provider).addFilterBefore(authenticationFilter(), AnonymousAuthenticationFilter.class)
                .authorizeRequests().requestMatchers(PROTECTED_URLS).authenticated()
                .anyRequest().permitAll()
                .and()
                .csrf().disable()
                .formLogin().disable()
                .httpBasic().disable()
                .logout().disable();
    }


    @Bean
    public AuthenticationFilter authenticationFilter() throws Exception {
        final AuthenticationFilter filter = new AuthenticationFilter(PROTECTED_URLS);
        filter.setAuthenticationManager(authenticationManager());
        //filter.setAuthenticationSuccessHandler(successHandler());
        filter.setAuthenticationFailureHandler(authenticationFailureHandler());
        return filter;
    }

//    @Bean
//    AuthenticationEntryPoint forbiddenEntryPoint() {
//        return new HttpStatusEntryPoint(HttpStatus.FORBIDDEN);
//    }

    @Bean
    public CustomAccessDeniedHandler accessDeniedHandler() {
        return new CustomAccessDeniedHandler();
    }

    @Bean
    public CustomAuthenticationEntryPoint authenticationEntryPoint() {
        return new CustomAuthenticationEntryPoint();
    }

    @Bean
    public CustomAuthenticationFailureHandler authenticationFailureHandler(){
        return new CustomAuthenticationFailureHandler();
    }

//    @Bean
//    RestAuthenticationSuccessHandler successHandler(){
//        return new RestAuthenticationSuccessHandler();
//    }
}
