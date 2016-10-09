package com.sda.iManu.dto;

import com.sda.iManu.domain.Client;
import com.sda.iManu.domain.Role;
import com.sda.iManu.domain.User;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Id;

/**
 * Created by teos on 2016-10-03.
 */
@Getter
@Setter
@ToString
public class ClientDto {

    @Id
    private String id;

    private String firstName;

    private String lastName;

    private String login;

    private Role role;

    public static ClientDto fromUser(final User user) {
        final ClientDto result = new ClientDto();
        result.setId(user.getId());
        result.setFirstName(user.getFirstName());
        result.setLastName(user.getLastName());
        result.setLogin(user.getLogin());
        result.setRole(user.getRole());
        return result;
    }
}
