package com.microservice.carDefectservice.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import static com.microservice.carDefectservice.enums.Permission.*;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

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
                    TEAMLEAD_DELETE,
                    OPERATOR_CREATE
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
            		OPERATOR_CREATE
            )
    );

    @Getter
    private final Set<Permission> permissions;

    //makes role class suitable for spring security
    public List<SimpleGrantedAuthority> getAuthorities() {
        var authorities = permissions.stream()
                .map(permission -> new SimpleGrantedAuthority(permission.getPermission()))
                .collect(Collectors.toList());
        authorities.add(new SimpleGrantedAuthority("ROLE_" + this.name()));
        return authorities;
    }
}
