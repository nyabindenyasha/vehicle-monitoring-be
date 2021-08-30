package com.car.tracking.commons.exceptions;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @author Nyabinde Nyasha
 * @created 1/9/2021
 * @project boilerplate
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ErrorMessage {

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "MM-dd-yyyy HH:mm:SSS", locale = "en_ZW", timezone = "Africa/Harare")
    private Date timestamp;

    private String message;

    private String path;

    private String exception;

    private String status;

    private String description;

}
