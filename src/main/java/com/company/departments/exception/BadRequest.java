package com.company.departments.exception;

public class BadRequest extends RuntimeException {

    private static final long serialVersionUID = -6434676093008579433L;
    private String message;

    public BadRequest() {
    }

    public BadRequest(String message) {
        super(message);
        this.message = message;
    }
}
