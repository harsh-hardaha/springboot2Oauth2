package com.demo.admin.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.autoconfigure.endpoint.web.WebEndpointProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;

@Configuration
public class AdminServerWebConfig extends WebSecurityConfigurerAdapter {
	
	

	@Autowired
	private WebEndpointProperties webEndpointProperties;
	
	@Override
    protected void configure(HttpSecurity http) throws Exception {
        // @formatter:off
        /*SavedRequestAwareAuthenticationSuccessHandler successHandler = new SavedRequestAwareAuthenticationSuccessHandler();
        successHandler.setTargetUrlParameter("redirectTo");
        successHandler.setDefaultTargetUrl("/");*/

        http.authorizeRequests()
            .antMatchers("/assets/**").permitAll() 
            .antMatchers("/").permitAll()
            .antMatchers("/**").permitAll()
            .anyRequest().authenticated() 
            .and()
        .csrf()
            .csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse())  
            .ignoringAntMatchers(
                "/instances",   
                "/admin/**"  
            );
        // @formatter:on
    }
}
