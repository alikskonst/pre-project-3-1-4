package edu.kata.task314.converter;

import edu.kata.task314.dto.RoleDto;
import edu.kata.task314.entity.Role;
import org.mapstruct.Mapper;

@Mapper
public interface RoleConverter {

    RoleDto convert(Role role);
}
