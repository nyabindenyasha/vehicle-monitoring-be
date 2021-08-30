package com.car.tracking.commons.exceptions;

/**
 * @author Nyabinde Nyasha
 * @created 12/18/2020
 * @project boilerplate
 */

public class ItemCannotBeDeletedException extends RuntimeException {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    public ItemCannotBeDeletedException(String message) {
        super(message + " cannot be deleted!!!");
    }
}
