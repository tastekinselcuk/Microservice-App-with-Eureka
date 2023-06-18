package com.microservice.authservice.security.auth;

import lombok.RequiredArgsConstructor;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Authentication Controller
 * <br><br>
 * Mappings for user registration and login are created here
 *
 */
@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthenticationController {

	@Autowired
	private AuthenticationService service;
     
    /**
     * Register user, if applicable save user.
     * <br><br>
     * Request body sample:
     * <pre>
     *     password= Selcuk123 - $2a$10$BF9UFNgyf5hNqGI76XILvOotZ.519SJMmHuxgU7jAZ9Zjk4WBD37G
     *     email= selcuk@gmail.com
     * </pre>
     *
     * @param RegisterRequest request - contains name, surname, email and password.
     * @return a ResponseEntity containing a success message and JWT token
     */
	@PostMapping("/register")
    @PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<?> register(@RequestBody RegisterRequest request) {
	    AuthenticationResponse token = service.register(request);
	    String message = String.format("User '%s' registered successfully.", request.getEmail());

	    Map<String, Object> response = new HashMap<>();
	    response.put("message", message);
	    response.put("token", token.getToken());
	    
	    return ResponseEntity.status(HttpStatus.CREATED).body(response);
	}

  
    /**
     * Authenticate user, if correct return jwt token.
     * <br><br>
     * Request body sample:
     * <pre>
     *     password= Selcuk123 - $2a$10$BF9UFNgyf5hNqGI76XILvOotZ.519SJMmHuxgU7jAZ9Zjk4WBD37G
     *     email= selcuk@gmail.com
     * </pre>
     *
     * @param AuthenticationRequest request - contains email and password.
     * @return a ResponseEntity containing a success message and JWT token
     */
	@PostMapping("/authenticate")
	public ResponseEntity<?> authenticate(@RequestBody AuthenticationRequest request) {
	    AuthenticationResponse token = service.authenticate(request);
	    String message = String.format("User '%s' authenticated successfully.", request.getEmail());

	    Map<String, Object> response = new HashMap<>();
	    response.put("message", message);
	    response.put("token", token.getToken());
	    
	    return ResponseEntity.ok(response);
	}

	  

}