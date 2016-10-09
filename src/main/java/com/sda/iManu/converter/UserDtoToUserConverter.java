package com.sda.iManu.converter;

import com.sda.iManu.domain.User;
import com.sda.iManu.dto.UserDto;

/**
 * Created by teos on 2016-10-07.
 */
public class UserDtoToUserConverter implements IConverter<UserDto, User> {

    public User convert(UserDto usernDto) {
        final User result = new User(
                usernDto.getId(),
                usernDto.getFirstName(),
                usernDto.getLastName(),
                usernDto.getLogin(),
                usernDto.getPassword(),
                usernDto.getRole());
        return result;
    }
}
