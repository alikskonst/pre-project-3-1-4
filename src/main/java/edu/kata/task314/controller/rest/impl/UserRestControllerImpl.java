package edu.kata.task314.controller.rest.impl;

import edu.kata.task314.controller.rest.UserRestController;
import edu.kata.task314.converter.UserConverter;
import edu.kata.task314.dto.UserDto;
import edu.kata.task314.entity.User;
import lombok.AllArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@AllArgsConstructor
@RestController
@RequestMapping("/api/users")
public class UserRestControllerImpl implements UserRestController {

    private final UserConverter userConverter;

    @Override
    public UserDto me() {
        // todo: побросить ошибку в случае трабл
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return userConverter.convert(user);
    }
}
