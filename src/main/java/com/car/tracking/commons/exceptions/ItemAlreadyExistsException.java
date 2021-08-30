package com.car.tracking.commons.exceptions;

/**
 * @author Nyabinde Nyasha
 * @created 12/18/2020
 * @project boilerplate
 */

public class ItemAlreadyExistsException extends RuntimeException {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    public ItemAlreadyExistsException(String message) {
        super(message + " already exists!!!");
    }
}
