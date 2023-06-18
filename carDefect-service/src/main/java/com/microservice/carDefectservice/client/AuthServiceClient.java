// package com.carDefectService.carDefectService.client;

// import org.springframework.cloud.openfeign.FeignClient;
// import org.springframework.web.bind.annotation.PostMapping;
// import org.springframework.web.bind.annotation.RequestBody;
// import org.springframework.http.ResponseEntity;

// import com.authService.authService.security.auth.RegisterRequest;
// import com.authService.authService.security.auth.AuthenticationRequest;

// @FeignClient(name = "auth-service", path = "/v1/auth")
// public interface AuthServiceClient {

//     @PostMapping("/register")
//     ResponseEntity<?> register(@RequestBody RegisterRequest request);

//     @PostMapping("/authenticate")
//     ResponseEntity<?> authenticate(@RequestBody AuthenticationRequest request);
    
// }
