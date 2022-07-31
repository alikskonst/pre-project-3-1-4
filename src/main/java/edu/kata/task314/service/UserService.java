package edu.kata.task314.service;

import edu.kata.task314.dto.UserDto;
import edu.kata.task314.dto.UserRegisterDto;
import edu.kata.task314.entity.User;

import java.util.List;

public interface UserService {

    UserDto findOne(Long id);

    UserDto findOne(String login);

    List<UserDto> findAll();

    UserDto save(UserDto userDto);

    UserDto save(UserRegisterDto userRegisterDto);

    void remove(Long id);
}
