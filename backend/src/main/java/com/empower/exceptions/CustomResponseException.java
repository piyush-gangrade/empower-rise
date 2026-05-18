package com.empower.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class CustomResponseException extends ResponseStatusException {

    private final boolean status;
    private final int code;
    private final String message;

    public CustomResponseException(HttpStatus status, String message) {
        super(status, message);
        this.status = false;
        this.code = status.value();
        this.message = message;
    }

    public boolean getStatus() {
        return status;
    }

    public int getCode() {
        return code;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
