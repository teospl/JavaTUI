package com.sda.iManu.dto;

import com.sda.iManu.domain.Role;
import com.sda.iManu.domain.User;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.Id;

/**
 * Created by teos on 2016-10-07.
 */
@Getter
@Setter
@ToString
public class UserDto {


    @Id
    private String id;

    private String firstName;

    private String lastName;

    private String login;

    private String password;

    private Role role;


    public static UserDto fromUser(final User user) {
        final UserDto result = new UserDto();

        result.setId(user.getId());
        result.setFirstName(user.getFirstName());
        result.setLastName(user.getLastName());
        result.setLogin(user.getLogin());
        result.setPassword(user.getPassword());
        result.setRole(user.getRole());
        return result;
    }
}
