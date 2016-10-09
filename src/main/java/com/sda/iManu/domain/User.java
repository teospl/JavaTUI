package com.sda.iManu.domain;


import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.cloud.cloudfoundry.com.fasterxml.jackson.databind.ser.std.StdArraySerializers;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

/**
 * Represents customer data.
 */
@Setter
@Getter
@ToString
public class User {

    @Id
    private String id;

    private String firstName;

    private String lastName;

    @Indexed(unique = true)
    private String login;

    private String password;

    private Role role;

//    private Boolean isNew() {
//        return getId() != null;
//    }

    public User(final String id,final String firstName, final String lastName, final String login, String password, final Role role) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.login = login;
        this.password = password;
        this.role = role;
    }
}
