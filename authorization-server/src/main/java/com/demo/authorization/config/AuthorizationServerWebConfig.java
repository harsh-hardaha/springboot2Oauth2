package com.demo.authorization.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@EnableWebSecurity
public class AuthorizationServerWebConfig extends WebSecurityConfigurerAdapter {

	@Bean
	@Override
	public AuthenticationManager authenticationManagerBean() throws Exception {
		return super.authenticationManagerBean();
	}
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		/*http.formLogin().loginPage("/login").permitAll().and()
		.requestMatchers().antMatchers("/login", "/oauth/authorize", "/oauth/confirm_access").and()
		.authorizeRequests().anyRequest().authenticated().and().requestMatchers()
		.antMatchers("/admin/health").and().authorizeRequests().anyRequest().permitAll()
		.and().csrf().ignoringAntMatchers("/oauth/**", "/mgmt/**");*/
		/*http.formLogin().permitAll().and()
        .authorizeRequests().antMatchers("/login","/oauth/token","/oauth/check_token","/user","/admin/health")
        .permitAll().anyRequest().authenticated();*/
		
		/*http.formLogin().permitAll().and().requestMatchers()
		.antMatchers("/login", "/oauth/authorize", "/oauth/confirm_access, /ouath/token, /ouath/check_token").and()
		.authorizeRequests().anyRequest().authenticated().and().requestMatchers()
		.antMatchers("/admin/health").and().authorizeRequests().anyRequest().permitAll()
		.and().csrf().ignoringAntMatchers("/oauth/**", "/admin/**");
        */
		http.authorizeRequests().anyRequest().permitAll();
	}
	
	 @Override
	 public void configure(AuthenticationManagerBuilder authenticationMgr) throws Exception {
	        authenticationMgr.inMemoryAuthentication()
	        .withUser("useradmin").password("{noop}passwordadmin")
	            .authorities("ROLE_ADMIN").roles("ADMIN").and()
	            .withUser("user").password("{noop}password").authorities("ROLE_USER").roles("USER");
	 }
	 
	 @Autowired
	    public void globalUserDetails(AuthenticationManagerBuilder auth) throws Exception {
	        auth.inMemoryAuthentication()
	        .withUser("mary").password("{noop}koala").roles("USER").and()
	        .withUser("paul").password("{noop}emu").roles("USER");
	    }
}
