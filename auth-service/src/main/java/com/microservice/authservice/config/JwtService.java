package com.microservice.authservice.config;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.extern.log4j.Log4j2;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

/**
 * JWT Service
 * 
 * Service class for JSON Web Token (JWT) operations.
 *
 */

@Log4j2
@Service
public class JwtService {

  /**
   * The secret key used to sign and verify JWT tokens.
   */
  @Value("${application.security.jwt.secret-key}")
  private String secretKey;
  @Value("${application.security.jwt.expiration}")
  private long jwtExpiration;
  @Value("${application.security.jwt.refresh-token.expiration}")
  private long refreshExpiration;

  /**
   * Extracts the username from the specified JWT token.
   *
   * @param token the JWT token to extract the username from.
   * @return the username extracted from the specified JWT token.
   */
  public String extractUsername(String token) {
    log.info("Extracting username from token: {}", token);
    return extractClaim(token, Claims::getSubject);
  }

  /**
   * Extracts a claim from the specified JWT token using the specified claims resolver.
   *
   * @param token the JWT token to extract the claim from.
   * @param claimsResolver the function used to resolve the claim.
   * @param <T> the type of the claim.
   * @return the claim extracted from the specified JWT token.
   */
  public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
    log.info("Extracting claim from token: {}", token);
    final Claims claims = extractAllClaims(token);
    return claimsResolver.apply(claims);
  }

  /**
   * Generates a new JWT token for the specified user details.
   *
   * @param userDetails the user details to generate the JWT token for.
   * @return the new JWT token generated for the specified user details.
   */
  public String generateToken(UserDetails userDetails) {
    log.info("Generating token for user: {}", userDetails.getUsername());
    return generateToken(new HashMap<>(), userDetails);
  }

  /**
   * Generates a new JWT token for the specified user details and extra claims.
   *
   * @param extraClaims the extra claims to include in the new JWT token.
   * @param userDetails the user details to generate the JWT token for.
   * @return the new JWT token generated for the specified user details and extra claims.
   */
  public String generateToken(
      Map<String, Object> extraClaims,
      UserDetails userDetails
  ) {
    log.info("Generating token for user: {} with extra claims: {}", userDetails.getUsername(), extraClaims);
    return buildToken(extraClaims, userDetails, jwtExpiration);
  }

  public String generateRefreshToken(
      UserDetails userDetails
  ) {
    log.info("Generating refresh token for user: {}", userDetails.getUsername());
    return buildToken(new HashMap<>(), userDetails, refreshExpiration);
  }

  private String buildToken(
          Map<String, Object> extraClaims,
          UserDetails userDetails,
          long expiration
  ) {
    log.info("Building token for user: {}", userDetails.getUsername());
    return Jwts
            .builder()
            .setClaims(extraClaims)
            .setSubject(userDetails.getUsername())
            .setIssuedAt(new Date(System.currentTimeMillis()))
            .setExpiration(new Date(System.currentTimeMillis() + expiration))
            .signWith(getSignInKey(), SignatureAlgorithm.HS256)
            .compact();
  }

  /**
   * Checks if the provided JWT token is valid for the given user details.
   * 
   * @param token the JWT token to check
   * @param userDetails the user details to compare the token against
   * @return true if the token is valid for the user, false otherwise 
   */
  public boolean isTokenValid(String token, UserDetails userDetails) {
    log.info("Checking if token is valid for user: {}", userDetails.getUsername());
    final String username = extractUsername(token);
    return (username.equals(userDetails.getUsername())) && !isTokenExpired(token);
  }

  /**
   *Checks if the provided JWT token has expired.
   *
   *@param token the JWT token to check
   *@return true if the token has expired, false otherwise
   */
  private boolean isTokenExpired(String token) {
    log.info("Checking if token is expired: {}", token);
    return extractExpiration(token).before(new Date());
  }

  /**
   * Extracts the expiration date from the provided JWT token.
   * 
   * @param token the JWT token to extract the expiration date from
   * @return the expiration date of the token
   */
  private Date extractExpiration(String token) {
    log.info("Extracting expiration date from token: {}", token);
    return extractClaim(token, Claims::getExpiration);
  }

  /**
   * Extracts all the claims from the provided JWT token.
   * 
   * @param token the JWT token to extract the claims from
   * @return all the claims of the token
   */
  private Claims extractAllClaims(String token) {
    log.info("Extracting all claims from token: {}", token);
    return Jwts
        .parserBuilder()
        .setSigningKey(getSignInKey())
        .build()
        .parseClaimsJws(token)
        .getBody();
  }

  /**
   * Returns the secret key used to sign and verify JWT tokens.
   * 
   * @return the secret key used to sign and verify JWT tokens.
   */
  private Key getSignInKey() {
    log.info("Getting sign in key for token generation");
    byte[] keyBytes = Decoders.BASE64.decode(secretKey);
    return Keys.hmacShaKeyFor(keyBytes);
  }
}
