package edu.kata.task314.converter;

import edu.kata.task314.dto.UserDto;
import edu.kata.task314.entity.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = {RoleConverter.class})
public interface UserConverter {

    User convert(UserDto source);

    UserDto convert(User source);
}
