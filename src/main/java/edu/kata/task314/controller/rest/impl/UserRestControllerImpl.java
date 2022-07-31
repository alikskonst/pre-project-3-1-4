package edu.kata.task314.controller.rest.impl;

import edu.kata.task314.controller.rest.UserRestController;
import edu.kata.task314.dto.UserDto;
import edu.kata.task314.dto.UserRegisterDto;
import edu.kata.task314.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/api/users")
public class UserRestControllerImpl implements UserRestController {

    private final UserService userService;

    //------------------------------------------------------------------------------------------------------------------

    @Override
    public UserDto findOne(Long id) {
        return userService.findOne(id);
    }

    @Override
    public List<UserDto> findAll() {
        return userService.findAll();
    }

    @Override
    public UserDto save(UserDto userDto) {
        return userService.save(userDto);
    }

    @Override
    public UserDto register(UserRegisterDto userRegisterDto) {
        return userService.save(userRegisterDto);
    }

    @Override
    public void remove(Long id) {
        userService.remove(id);
    }
}
