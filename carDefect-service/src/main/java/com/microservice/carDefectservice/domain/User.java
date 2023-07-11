package com.microservice.carDefectservice.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.microservice.carDefectservice.enums.Role;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "_user")
public class User implements UserDetails {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;
  
  @Column(nullable = false)
  private String firstname;

  @Column(nullable = false)
  private String lastname;

  @Column(nullable = false, unique = true)
  private String email;

  @Column(nullable = false)
  private String password;

  @Enumerated(EnumType.STRING)
  private Role role;
  
  @Column(name = "is_deleted", nullable = false)
  private Boolean deleted = false;

  @OneToMany(mappedBy = "user")
  private List<Token> tokens;

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    return role.getAuthorities();
  }
  
  @Override
  public String getPassword() {
    return password;
  }

  @Override
  public String getUsername() {
    return email;
  }

  @Override
  public boolean isAccountNonExpired() {
    return true;
  }

  @Override
  public boolean isAccountNonLocked() {
    return true;
  }

  @Override
  public boolean isCredentialsNonExpired() {
    return true;
  }

  @Override
  public boolean isEnabled() {
    return true;
  }
  
  /**
   * Returns the full name of the user by combining the first name and last name.
   * @return The full name of the user.
   */
  public String getFullName() {
    return String.format("%s %s", firstname, lastname);
  }

  /**
   * Sets the full name of the user by splitting the input string into two parts: first name and last name.
   * @param fullName The full name of the user in the format "First Name Last Name".
   * @throws IllegalArgumentException If the input string does not have exactly two parts: first name and last name.
   */
  public void setFullName(String fullName) {
    String[] parts = fullName.split(" ");
    if (parts.length != 2) {
      throw new IllegalArgumentException("Full name must have exactly two parts: firstname and lastname.");
    }
    this.firstname = parts[0];
    this.lastname = parts[1];
  }

  /**
   * Returns a list of full names for the given list of users.
   * @param users The list of users to get full names from.
   * @return A list of full names for the given list of users.
   */
  public static List<String> getFullNames(List<User> users) {
    return users.stream()
        .map(User::getFullName)
        .collect(Collectors.toList());
  }
}
