package com.demo.resource.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.autoconfigure.endpoint.web.WebEndpointProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;

@EnableResourceServer
@Configuration
public class ResourceApplicationWebSecurityConfig extends ResourceServerConfigurerAdapter {
	
	private static final String[] AUTH_WHITELIST = {
            // -- swagger ui
            "/v2/api-docs",
            "/swagger-resources",
            "/swagger-resources/**",
            "/configuration/ui",
            "/configuration/security",
            "/swagger-ui.html",
            "/webjars/**"
            // other public endpoints of your API may be appended to this array
    };
	
	@Autowired
	private WebEndpointProperties webEndpointProperties;

	@Override
    public void configure(HttpSecurity http) throws Exception{
        http.authorizeRequests().antMatchers(webEndpointProperties.getBasePath()+"/health").permitAll().and()
        .authorizeRequests().antMatchers("/swagger-ui.html").permitAll().and()
        .authorizeRequests().antMatchers(AUTH_WHITELIST).permitAll().and()
        .authorizeRequests().antMatchers("/nonsecured").permitAll()
        .anyRequest().authenticated();
    }
}
