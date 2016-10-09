package com.sda.iManu.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

/**
 * Created by teos on 2016-09-28.
 */
@Getter
@Setter
@ToString
public class LoginDto {

        @NotEmpty
        @Length(min = 6, max= 20)
        private String login;

        @NotEmpty
        @Length(min = 6, max= 20)
        private String password;

}
