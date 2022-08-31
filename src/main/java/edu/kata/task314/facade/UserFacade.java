package edu.kata.task314.facade;

import edu.kata.task314.dto.RoleDto;
import edu.kata.task314.dto.UserDto;

import java.util.List;
import java.util.Set;

public interface UserFacade {

    UserDto findOneUser(Long userId);

    UserDto findOneUser(String login);

    List<UserDto> findAllUsers();

    UserDto saveUser(UserDto userDto);

    void removeUser(Long userId);

    //------------------------------------------------------------------------------------------------------------------

    RoleDto findOneRole(Long roleId);

    Set<RoleDto> findAllRoles();

    Set<RoleDto> findAllRoles(Long userId);
}
