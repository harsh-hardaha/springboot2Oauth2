package com.demo.admin.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;

@Configuration
@EnableWebSecurity
@Order(101)
public class AdminServerWebConfig extends WebSecurityConfigurerAdapter {

	@Override
    protected void configure(HttpSecurity http) throws Exception {

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

	}
}
