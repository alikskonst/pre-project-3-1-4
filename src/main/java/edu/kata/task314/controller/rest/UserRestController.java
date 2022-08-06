package edu.kata.task314.controller.rest;

import edu.kata.task314.dto.UserDto;
import org.springframework.web.bind.annotation.GetMapping;

public interface UserRestController {

    @GetMapping("/me")
    UserDto me();
}
