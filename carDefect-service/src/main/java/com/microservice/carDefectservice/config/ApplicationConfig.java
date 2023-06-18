package com.microservice.carDefectservice.config;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.microservice.carDefectservice.repository.UserRepository;



/**
 * This class is used to configure general application settings, such as user authentication, for use in our program.
 */

@Log4j2
@Configuration
@RequiredArgsConstructor
public class ApplicationConfig {

  private final UserRepository repository;

  /**
   * Creates an instance of user details service.
   * 
   * @return An instance of the user details service
  */
  @Bean
  public UserDetailsService userDetailsService() {
	log.debug("Creating user details service bean");
    return username -> repository.findByEmail(username)
        .orElseThrow(() -> new UsernameNotFoundException("User not found"));
  }

  /**
   * Creates an authentication provider instance for providing authentication.
   * 
   * @return An instance of the authentication provider
   */
  @Bean
  public AuthenticationProvider authenticationProvider() {
    log.debug("Creating authentication provider bean");
    DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
    authProvider.setUserDetailsService(userDetailsService());
    authProvider.setPasswordEncoder(passwordEncoder());
    return authProvider;
  }

  /**
   * Creates an instance of the authentication manager.
   * 
   * @param config The authentication configuration
   * @return An instance of the authentication manager
   * @throws Exception
   */
  @Bean
  public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
    log.debug("Creating authentication manager bean");
    return config.getAuthenticationManager();
  }

  /**
   * Creates an instance of the password encoder.
   * 
   * @return An instance of the password encoder
   */
  @Bean
  public PasswordEncoder passwordEncoder() {
    log.debug("Creating password encoder bean");
    return new BCryptPasswordEncoder();
  }

}