//package com.carDefectService.carDefectService.security.auth;
//
//import lombok.RequiredArgsConstructor;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//
///**
// * Authentication Controller
// * <br><br>
// * Mappings for user registration and login are created here
// *
// */
//@RestController
//@RequestMapping("/api/auth")
//@RequiredArgsConstructor
//public class AuthenticationController {
//
//	@Autowired
//	private AuthenticationService service;
//     
//    /**
//     * Register user, if applicable save user.
//     * <br><br>
//     * Request body sample:
//     * <pre>
//     *     password= Selcuk123 - $2a$10$BF9UFNgyf5hNqGI76XILvOotZ.519SJMmHuxgU7jAZ9Zjk4WBD37G
//     *     email= selcuk@gmail.com
//     * </pre>
//     *
//     * @param RegisterRequest request - contains name, surname, email and password.
//     * @return JWT token
//     */
//	  @PostMapping("/register")
//	  public ResponseEntity<?> register(@RequestBody RegisterRequest request) {
//		  try {
//			    return ResponseEntity.ok(service.register(request));
//	    	} catch (RuntimeException e) {
//	            return ResponseEntity.badRequest().body(e.getMessage());
//	        }
//	  }
//  
//    /**
//     * Authenticate user, if correct return jwt token.
//     * <br><br>
//     * Request body sample:
//     * <pre>
//     *     password= Selcuk123 - $2a$10$BF9UFNgyf5hNqGI76XILvOotZ.519SJMmHuxgU7jAZ9Zjk4WBD37G
//     *     email= selcuk@gmail.com
//     * </pre>
//     *
//     * @param AuthenticationRequest request - contains email and password.
//     * @return JWT token
//     */
//	  @PostMapping("/authenticate")
//	  public ResponseEntity<?> authenticate(@RequestBody AuthenticationRequest request) {
//		  try {
//			    return ResponseEntity.ok(service.authenticate(request));
//		  } catch (Exception e) {
//	            return ResponseEntity.badRequest().body(e.getMessage());
//		}
//	  }
//	  
//
//}