package com.sda.iManu.dto;

import com.sda.iManu.domain.Role;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.Id;

/**
 * Obiekt uzywany podczas rejestracji.
 */
@Getter
@Setter
@ToString
public class RegistrationDto {

    @Id
    private String id;

    @NotEmpty
    @Length(min = 2, max= 20)
    private String firstName;

    @NotEmpty
    @Length(min = 2, max= 20)
    private String lastName;

    @NotEmpty
    @Length(min = 6, max= 20)
    private String login;

    @NotEmpty
    @Length(min = 6, max= 20)
    private String password;

    @NotEmpty
    @Length(min = 6, max= 20)
    private String passwordRepeat;

    private Role role;
}
