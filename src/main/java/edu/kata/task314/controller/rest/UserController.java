package edu.kata.task314.controller.rest;

import edu.kata.task314.dto.UserDto;
import edu.kata.task314.dto.UserRegisterDto;
import org.springframework.web.bind.annotation.*;

import java.util.List;

public interface UserController {

    @GetMapping
    UserDto findOne(@RequestParam Long id);

    @GetMapping("/all")
    List<UserDto> findAll();

    @PostMapping
    UserDto save(@RequestBody UserDto userDto);

    @PostMapping("/register")
    UserDto register(@RequestBody UserRegisterDto userRegisterDto);

    @DeleteMapping("/remove")
    void remove(@RequestParam Long id);
}
