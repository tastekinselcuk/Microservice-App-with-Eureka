package com.microservice.authservice.user;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum Permission {

    ADMIN_READ("admin:read"),
    ADMIN_UPDATE("admin:update"),
    ADMIN_CREATE("admin:create"),
    ADMIN_DELETE("admin:delete"),
    TEAMLEAD_READ("teamlead:read"),
    TEAMLEAD_UPDATE("teamlead:update"),
    TEAMLEAD_CREATE("teamlead:create"),
    TEAMLEAD_DELETE("teamlead:delete"),
    OPERATOR_READ("operator:read");

    @Getter
    private final String permission;
}
