package com.microservice.carDefectservice.domain.user;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum Permission {

    ADMIN_READ("admin:read"),
    ADMIN_UPDATE("admin:update"),
    ADMIN_CREATE("admin:create"),
    ADMIN_DELETE("admin:delete"),
    TEAMLEAD_READ("management:read"),
    TEAMLEAD_UPDATE("management:update"),
    TEAMLEAD_CREATE("management:create"),
    TEAMLEAD_DELETE("management:delete"),
    OPERATOR_READ("operator:read");

    @Getter
    private final String permission;
}
