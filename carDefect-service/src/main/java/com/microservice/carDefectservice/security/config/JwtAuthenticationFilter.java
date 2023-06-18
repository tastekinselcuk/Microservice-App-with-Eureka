package com.microservice.carDefectservice.security.config;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

/**
 * Authentication filter
 * 
 * Each request sent from the client will first refer here
 * and check if the JWT token exists and is still valid.
 *
 */
@Log4j2
@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {


	  private final JwtService jwtService;
	  private final UserDetailsService userDetailsService;

	  @Override
	  protected void doFilterInternal(@NonNull HttpServletRequest request, @NonNull HttpServletResponse response, @NonNull FilterChain filterChain)
			 throws ServletException, IOException {
	    log.info("Starting authentication filter..");

	    final String authHeader = request.getHeader("Authorization");
	    final String jwt;
	    final String userEmail;
	    if (authHeader == null || !authHeader.startsWith("Bearer ")) {
	      log.debug("Authorization header is missing or invalid: {}", authHeader);
	      filterChain.doFilter(request, response);
	      return;
	    }
	    jwt = authHeader.substring(7);
	    log.debug("Extracted JWT token from authorization header: {}", jwt);
	    userEmail = jwtService.extractUsername(jwt);
	    log.debug("Extracted userEmail from authorization token: {}", userEmail);

	    if (userEmail != null && SecurityContextHolder.getContext().getAuthentication() == null) {
	      UserDetails userDetails = this.userDetailsService.loadUserByUsername(userEmail);
	      log.info("Proceeding to next filter on filter chain...");

	      if (jwtService.isTokenValid(jwt, userDetails)) {
	        log.debug("Authorization token is valid for userEmail: {}", userEmail);

	        UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
	            userDetails,
	            null,
	            userDetails.getAuthorities()
	        );
	        authToken.setDetails(
	            new WebAuthenticationDetailsSource().buildDetails(request)
	        );
	        SecurityContextHolder.getContext().setAuthentication(authToken);

	        log.info("User with userEmail: {} successfully authenticated", userEmail);
	      } else {
	        log.warn("Authorization token is not valid for userEmail: {}", userEmail);
	      }
	    } else {
	      log.debug("User not authenticated, but no need for authentication.");
	    }
	    filterChain.doFilter(request, response);
	  }

}

