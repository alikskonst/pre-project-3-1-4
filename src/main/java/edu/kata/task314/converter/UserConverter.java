package edu.kata.task314.converter;

import edu.kata.task314.dto.UserDto;
import edu.kata.task314.dto.UserRegisterDto;
import edu.kata.task314.entity.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserConverter {

    User convert(UserDto source);

    User convert(UserRegisterDto source);

    UserDto convert(User source);
}
