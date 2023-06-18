package com.microservice.userTerminalManagementservice.exception;

/**
 * Response type for the responses that is returned from controller endpoints.
 *
 * This enum class is primarily aimed to be used inside a @{@link com.j32bit.inviso.utils.GenericResponse} object,
 * but it could be extended and used by other objects as well.
 */
public enum ResponseType {

    ERROR,
    SUCCESS;

    @Override
    public String toString() {
        return this.name();
    }
}