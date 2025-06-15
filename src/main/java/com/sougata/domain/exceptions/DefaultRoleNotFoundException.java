package com.sougata.domain.exceptions;

public class DefaultRoleNotFoundException extends RuntimeException {
    public DefaultRoleNotFoundException(String message) {
        super(message);
    }

    public DefaultRoleNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
