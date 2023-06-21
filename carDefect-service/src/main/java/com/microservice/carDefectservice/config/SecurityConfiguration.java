package com.microservice.carDefectservice.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.logout.LogoutHandler;

import com.microservice.carDefectservice.security.config.JwtAuthenticationFilter;

import static com.microservice.carDefectservice.domain.user.Role.ADMIN;
import static com.microservice.carDefectservice.domain.user.Role.TEAMLEAD;
import static com.microservice.carDefectservice.domain.user.Role.OPERATOR;


//Configuration for HTTP requests and roles

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
@EnableMethodSecurity
public class SecurityConfiguration {

  private final JwtAuthenticationFilter jwtAuthFilter;
  private final AuthenticationProvider authenticationProvider;
  private final LogoutHandler logoutHandler;
  
  @Bean
  public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
	  
    http
    	//CSRF(Cross-Site Request Forgery)-CORS(Cross-Origin Resource Sharing)
        .csrf().disable()
        .cors().disable() 
        //AuthService Access
        .authorizeHttpRequests()
    	.requestMatchers("/api/auth/**").permitAll()
    	//CarDefectService Access
    	.requestMatchers("/api/carDefect/save").hasAnyAuthority(OPERATOR.name())
    	.requestMatchers("/api/carDefectImage/**").hasAnyRole(TEAMLEAD.name())
    	.requestMatchers("/api/page/**").hasAnyRole(TEAMLEAD.name())
        //ManagementService Access
    	.requestMatchers("/api/terminal/manager/**").hasAnyRole(ADMIN.name())
    	.requestMatchers("/api/user/manager/**").hasAnyRole(ADMIN.name())
        //Api
    	.requestMatchers("/api/users/**").hasAnyRole(ADMIN.name())
    	.requestMatchers("/api/car/**").hasAnyRole(ADMIN.name())
    	.requestMatchers("/api/defect/**").hasAnyRole(ADMIN.name(), TEAMLEAD.name())
    	.requestMatchers("/api/terminals/**").hasAnyRole(ADMIN.name(), TEAMLEAD.name())
        .requestMatchers("/api/**").authenticated()

        
        .anyRequest()
        .authenticated()
    	.and()	
        .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
        .and()        
        .authenticationProvider(authenticationProvider)
        .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class)
        .logout()
        .logoutUrl("/api/auth/logout")
        .addLogoutHandler(logoutHandler)
        .logoutSuccessHandler((request, response, authentication) -> SecurityContextHolder.clearContext());
        return http.build();  
  }

}
