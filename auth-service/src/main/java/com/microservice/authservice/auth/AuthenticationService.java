package com.microservice.authservice.auth;

import com.microservice.authservice.config.JwtService;
import com.microservice.authservice.exception.AppException;
import com.microservice.authservice.repository.UserRepository;
import com.microservice.authservice.token.Token;
import com.microservice.authservice.token.TokenRepository;
import com.microservice.authservice.token.TokenType;
import com.microservice.authservice.user.User;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.io.IOException;

/**
 * Authentication Service
 * 
 * procedures for registration and login are written here
 *
 */
@Log4j2
@Service
@RequiredArgsConstructor
public class AuthenticationService {
  private final UserRepository repository;
  private final TokenRepository tokenRepository;
  private final PasswordEncoder passwordEncoder;
  private final JwtService jwtService;
  private final AuthenticationManager authenticationManager;

  /**
   * Register service, if applicable save user and return token.
   *
   * @param RegisterRequest request - contains name, surname, email, password and role(s).
   * @return JWT token
   */
  public AuthenticationResponse register(RegisterRequest request) {
		log.trace("registration started");

	    if(repository.existsByEmailAndDeletedFalse(request.getEmail())) {
	        throw new AppException(
	                HttpStatus.BAD_REQUEST,
	                "Email Already Exist",
	                "Provided email already exist!"
	        );}
	    if (request.getPassword().length() < 8 || !request.getPassword().matches(".*[a-z].*") || !request.getPassword().matches(".*[A-Z].*")) {
	        throw new AppException(
	                HttpStatus.BAD_REQUEST,
	                "Invalid Password",
	                "Password should contain at least one lowercase letter, one uppercase letter, and be at least 8 characters long!"
	        );}
	    var user = User.builder()
	        .firstname(request.getFirstname())
	        .lastname(request.getLastname())
	        .email(request.getEmail())
	        .password(passwordEncoder.encode(request.getPassword()))
	        .role(request.getRole())
	        .build();
	    var savedUser = repository.save(user);
	    var jwtToken = jwtService.generateToken(user);
	    var refreshToken = jwtService.generateRefreshToken(user);
	    saveUserToken(savedUser, jwtToken);
    	log.info("registration completed for {}", user.getFirstname());
	    return AuthenticationResponse.builder()
	        .accessToken(jwtToken)
	            .refreshToken(refreshToken)
	        .build();
  }

  /**
   * Login service, if applicable give permission for login and return token.
   *
   * @param AuthenticationRequest request - contains email and password.
   * @return JWT token
   */
  public AuthenticationResponse authenticate(AuthenticationRequest request) {
		log.trace("authentication started");
		
		try {
		    authenticationManager.authenticate(
		            new UsernamePasswordAuthenticationToken(
		                request.getEmail(),
		                request.getPassword()
		            )
		        );
		        var user = repository.findByEmail(request.getEmail())
		            .orElseThrow();
		        var jwtToken = jwtService.generateToken(user);
		        var refreshToken = jwtService.generateRefreshToken(user);
		        revokeAllUserTokens(user);
		        saveUserToken(user, jwtToken);
		    	log.info("authentication completed for {}", user.getFirstname());
		        return AuthenticationResponse.builder()
		            .accessToken(jwtToken)
		                .refreshToken(refreshToken)
		            .build();
			
		} catch (Exception e) {
	        throw new AppException(
	                HttpStatus.UNAUTHORIZED,
	                "Invalid email or password",
	                "The email or password provided is invalid!"
	        );	}
	    
  }

  private void saveUserToken(User user, String jwtToken) {
    var token = Token.builder()
        .user(user)
        .token(jwtToken)
        .tokenType(TokenType.BEARER)
        .expired(false)
        .revoked(false)
        .build();
    tokenRepository.save(token);
  }

  private void revokeAllUserTokens(User user) {
    var validUserTokens = tokenRepository.findAllValidTokenByUser(user.getId());
    if (validUserTokens.isEmpty())
      return;
    validUserTokens.forEach(token -> {
      token.setExpired(true);
      token.setRevoked(true);
    });
    tokenRepository.saveAll(validUserTokens);
  }

  public void refreshToken(
          HttpServletRequest request,
          HttpServletResponse response
  ) throws IOException {
    final String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
    final String refreshToken;
    final String userEmail;
    if (authHeader == null ||!authHeader.startsWith("Bearer ")) {
      return;
    }
    refreshToken = authHeader.substring(7);
    userEmail = jwtService.extractUsername(refreshToken);
    if (userEmail != null) {
      var user = this.repository.findByEmail(userEmail)
              .orElseThrow();
      if (jwtService.isTokenValid(refreshToken, user)) {
        var accessToken = jwtService.generateToken(user);
        revokeAllUserTokens(user);
        saveUserToken(user, accessToken);
        var authResponse = AuthenticationResponse.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();
        new ObjectMapper().writeValue(response.getOutputStream(), authResponse);
      }
    }
  }
}
