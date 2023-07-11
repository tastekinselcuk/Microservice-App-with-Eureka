package com.microservice.userTerminalManagementservice.enums;

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
    OPERATOR_CREATE("operator:create");

    @Getter
    private final String permission;
}
