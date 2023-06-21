package com.microservice.authservice.user;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static com.microservice.authservice.user.Permission.*;

@RequiredArgsConstructor
public enum Role {

    ADMIN(
            Set.of(
                    ADMIN_READ,
                    ADMIN_UPDATE,
                    ADMIN_CREATE,
                    ADMIN_DELETE,
                    TEAMLEAD_READ,
                    TEAMLEAD_UPDATE,
                    TEAMLEAD_CREATE,
                    TEAMLEAD_DELETE
            )
    ),
    TEAMLEAD(
            Set.of(
            		TEAMLEAD_READ,
            		TEAMLEAD_UPDATE,
            		TEAMLEAD_DELETE,
            		TEAMLEAD_CREATE
            )
    ),
    OPERATOR(
            Set.of(
                    OPERATOR_READ
            )
    );

    @Getter
    private final Set<Permission> permissions;

    public List<SimpleGrantedAuthority> getAuthorities() {
        var authorities = permissions.stream()
                .map(permission -> new SimpleGrantedAuthority(permission.getPermission()))
                .collect(Collectors.toList());
        authorities.add(new SimpleGrantedAuthority("ROLE_" + this.name()));
        return authorities;
    }
}
