package com.sda.iManu.domain;

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
public class Client {
    @Id
    private String id;

    private String firstName;

    private String lastName;

    private String login;

    private Role role;

    public Client(String id, String firstName, String lastName, String login, Role role) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.login = login;
        this.role = role;
    }
}
