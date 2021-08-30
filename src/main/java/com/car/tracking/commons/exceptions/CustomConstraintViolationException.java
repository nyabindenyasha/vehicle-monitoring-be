package com.car.tracking.commons.exceptions;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author Nyabinde Nyasha
 * @created 1/9/2021
 * @project boilerplate
 */
public class CustomConstraintViolationException extends ConstraintViolationException {

    public CustomConstraintViolationException(Set<? extends ConstraintViolation<?>> constraintViolations) {

        super(toString(constraintViolations), constraintViolations);

    }

    private static String toString(Set<? extends ConstraintViolation<?>> constraintViolations) {
        return constraintViolations.stream()
                .map(cv -> cv == null ? "null" : cv.getMessage())
                .collect(Collectors.joining(", \n"));
    }
}
