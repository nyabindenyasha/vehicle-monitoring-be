package com.car.tracking.users.context;

import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

/**
 * @author Nyabinde Nyasha
 * @created 3/25/2021
 * @project todo-app
 */

@Data
public class UserAccountUpdateContext {

    private long id;

    @NotBlank(message = "First name is required")
    @Size(min = 3, max = 50)
    private String firstName;

    @NotBlank(message = "Lastname is required")
    @Size(min = 3, max = 50)
    private String lastName;

    @Size(max = 100)
    @Email
    private String email;

}
