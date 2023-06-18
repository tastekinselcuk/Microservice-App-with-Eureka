package com.microservice.userTerminalManagementservice.config;

import lombok.RequiredArgsConstructor;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.microservice.userTerminalManagementservice.enums.Role;
import com.microservice.userTerminalManagementservice.security.config.JwtAuthenticationFilter;

//Configuration for HTTP requests and roles

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfiguration {

	  private final JwtAuthenticationFilter jwtAuthFilter;
	  private final AuthenticationProvider authenticationProvider;

	  @Bean
	  public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
	    http
	    	//CSRF(Cross-Site Request Forgery)-CORS(Cross-Origin Resource Sharing)
	        .csrf().disable()
	        .cors().disable() 
	        //AuthService Access
	        .authorizeHttpRequests()
	    	.requestMatchers("/api/auth/register").permitAll()
	    	.requestMatchers("/api/auth/authenticate").permitAll()
	    	//CarDefectService Access
	    	.requestMatchers("/api/carDefect/save").hasAnyAuthority(Role.ADMIN.name())
	    	.requestMatchers("/api/carDefectImage/**").hasRole("TEAMLEAD")
	    	.requestMatchers("/api/page/**").hasRole("TEAMLEAD")
	        //ManagementService Access
	    	.requestMatchers("/api/terminal/manager/**").hasRole("ADMIN")
	    	.requestMatchers("/api/user/manager/**").hasRole("ADMIN")
	        //Api
	    	.requestMatchers("/api/users/**").hasRole("ADMIN")
	    	.requestMatchers("/api/car/**").hasRole("ADMIN")
	    	.requestMatchers("/api/defect/**").hasRole("ADMIN")
	    	.requestMatchers("/api/terminals/**").hasRole("ADMIN")
	        .requestMatchers("/api/**").authenticated()

	        
	    	.and()	
	        .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED)
	        
	        .and()        
	        .authenticationProvider(authenticationProvider).addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);  
	    return http.build();
	    
	  }
  
 
}