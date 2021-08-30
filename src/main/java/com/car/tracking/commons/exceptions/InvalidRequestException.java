package com.car.tracking.commons.exceptions;

/**
 * @author Nyabinde Nyasha
 * @created 12/18/2020
 * @project boilerplate
 */

public class InvalidRequestException extends RuntimeException {

    public InvalidRequestException(String message) {
        super(message);
    }

}
