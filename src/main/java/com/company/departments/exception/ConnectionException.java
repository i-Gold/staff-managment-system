package com.company.departments.exception;

public class ConnectionException extends RuntimeException {

    private static final long serialVersionUID = -722423852847227426L;
    private String message;

    public ConnectionException(String message) {
        super(message);
        this.message = message;
    }
}
