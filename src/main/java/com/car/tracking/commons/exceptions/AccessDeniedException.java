package com.car.tracking.commons.exceptions;

/**
 * @author Nyabinde Nyasha
 * @created 1/9/2021
 * @project boilerplate
 */
public class AccessDeniedException extends RuntimeException {

    public AccessDeniedException(String message) {
        super(message);
    }
}
