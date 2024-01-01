package com.example.demo.security;


import java.util.Collections;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.preauth.RequestHeaderAuthenticationFilter;
import org.springframework.security.web.header.HeaderWriterFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import jakarta.servlet.http.HttpServletResponse;
@Configuration
@EnableWebSecurity
public class SecurityConfig {
	// requestHeaderAuthenticationFilter(), HeaderWriterFilter.class
		@Autowired 
		RequestHeaderAuthenticationProvider requestHeaderAuthenticationProvider;
		@Bean 
	    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
			 http.cors().and()
	          .csrf()
	          .disable()
	          .sessionManagement()
	          .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
	          .and()
	          .addFilterAfter(requestHeaderAuthenticationFilter(), HeaderWriterFilter.class)
	          .authorizeHttpRequests()
	          .requestMatchers("/secured/admin").authenticated().requestMatchers("/secured/health").permitAll()
	          .and().exceptionHandling(ex->ex.accessDeniedPage("/"));
			 	return http.build();
	    }
	 @Bean
	 public RequestHeaderAuthenticationFilter requestHeaderAuthenticationFilter() {
	     RequestHeaderAuthenticationFilter filter = new RequestHeaderAuthenticationFilter();
	     filter.setPrincipalRequestHeader("x-auth-secret-key");
	     filter.setExceptionIfHeaderMissing(false);
	     filter.setRequiresAuthenticationRequestMatcher(new AntPathRequestMatcher("/secured/admin"));
	     filter.setAuthenticationManager(authenticationManager());
	     return filter;
	 }
	 
	 @Bean
	 protected AuthenticationManager authenticationManager() {
	     return new ProviderManager(Collections.singletonList(requestHeaderAuthenticationProvider));
	 }
	 
	}
