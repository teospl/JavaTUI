package com.sda.iManu.converter;

import com.sda.iManu.domain.User;
import com.sda.iManu.dto.RegistrationDto;

/**
 * Konwertuje obiekt RegistrationDto na obiekt domenowy User.
 */
public class RegistrationDtoToUserConverter
        implements IConverter<RegistrationDto,User>{

    public User convert(RegistrationDto registrationDto) {
        final User result = new User(
                registrationDto.getId(),
                registrationDto.getFirstName(),
                registrationDto.getLastName(),
                registrationDto.getLogin(),
                registrationDto.getPassword(),
                registrationDto.getRole());
        return result;
    }
}
