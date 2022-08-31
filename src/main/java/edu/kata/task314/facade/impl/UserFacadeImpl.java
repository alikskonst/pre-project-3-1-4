package edu.kata.task314.facade.impl;

import edu.kata.task314.converter.RoleConverter;
import edu.kata.task314.converter.UserConverter;
import edu.kata.task314.dto.RoleDto;
import edu.kata.task314.dto.UserDto;
import edu.kata.task314.entity.Role;
import edu.kata.task314.entity.User;
import edu.kata.task314.facade.UserFacade;
import edu.kata.task314.service.RoleService;
import edu.kata.task314.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@AllArgsConstructor
@Component
public class UserFacadeImpl implements UserFacade {

    private final UserService userService;
    private final RoleService roleService;
    private final UserConverter userConverter;
    private final RoleConverter roleConverter;

    //------------------------------------------------------------------------------------------------------------------

    @Override
    public UserDto findOneUser(Long userId) {
        return userConverter.convert(userService.findOne(userId));
    }

    @Override
    public UserDto findOneUser(String login) {
        return userConverter.convert(userService.findOne(login));
    }

    @Override
    public List<UserDto> findAllUsers() {
        return convertUsers(userService.findAll());
    }

    @Override
    public UserDto saveUser(UserDto userDto) {
        return userConverter.convert(
                userService.save(
                        userConverter.convert(userDto)
                )
        );
    }

    @Override
    public void removeUser(Long userId) {
        userService.remove(userId);
    }

    //------------------------------------------------------------------------------------------------------------------

    @Override
    public RoleDto findOneRole(Long roleId) {
        return null;
    }

    @Override
    public Set<RoleDto> findAllRoles() {
        return convertRoles(roleService.findAll());
    }

    @Override
    public Set<RoleDto> findAllRoles(Long userId) {
        return convertRoles(roleService.findAll(userId));
    }

    //------------------------------------------------------------------------------------------------------------------

    private List<UserDto> convertUsers(List<User> userList) {
        return userList.isEmpty() ?
                Collections.emptyList() :
                userList.stream().map(userConverter::convert).collect(Collectors.toList());
    }

    private Set<RoleDto> convertRoles(List<Role> roleList) {
        return roleList.isEmpty() ?
                Collections.emptySet() :
                roleList.stream().map(roleConverter::convert).collect(Collectors.toSet());
    }
}
