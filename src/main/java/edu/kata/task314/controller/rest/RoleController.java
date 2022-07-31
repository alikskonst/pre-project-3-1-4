package edu.kata.task314.controller.rest;

import edu.kata.task314.dto.RoleDto;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

public interface RoleController {

    @GetMapping
    RoleDto findOne(Long id);

    @GetMapping("/all")
    List<RoleDto> findAll();
}
