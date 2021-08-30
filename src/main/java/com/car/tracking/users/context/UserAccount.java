package com.car.tracking.users.context;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.car.tracking.commons.jpa.BaseEntity;
import lombok.Data;
import org.hibernate.annotations.NaturalId;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

/**
 * @author Nyabinde Nyasha
 * @created 3/25/2021
 * @project todo-app
 */

@Data
@Entity
public class UserAccount extends BaseEntity {

    @NaturalId
    @NotBlank
    @Size(max = 50)
    private String username;

    @Email
    private String email;

    @NotBlank
    @Size(min = 3, max = 50)
    private String firstName;

    @NotBlank
    @Size(min = 3, max = 50)
    private String lastName;

    @Transient
    private String fullName;

    @Transient
    private String token;

    private boolean isAdmin;

    @JsonIgnore
    private String password;

    public static UserAccount fromCommand(UserAccountCreateContext request) {

        if (request == null) {
            return null;
        }

        UserAccount userAccount = new UserAccount();
        userAccount.setUsername(request.getUsername());
        userAccount.setFirstName(request.getFirstName());
        userAccount.setLastName(request.getLastName());
        userAccount.setEmail(request.getEmail());
        userAccount.setPassword(request.getPassword());
        userAccount.setAdmin(request.isAdmin());
        return userAccount;
    }

    public void update(UserAccountUpdateContext request) {
        this.setFirstName(request.getFirstName());
        this.setLastName(request.getLastName());
        this.setEmail(request.getEmail());
    }

    @PostLoad
    public void postLoad() {
        this.fullName = this.firstName + " " + this.lastName;
    }

}
