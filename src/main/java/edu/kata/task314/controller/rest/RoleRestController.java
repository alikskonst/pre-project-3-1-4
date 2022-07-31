package edu.kata.task314.controller.rest;

import edu.kata.task314.dto.RoleDto;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Set;

public interface RoleRestController {

    @GetMapping
    RoleDto findOne(Long id);

    @GetMapping("/all")
    Set<RoleDto> findAll();
}
