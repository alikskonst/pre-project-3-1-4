package edu.kata.task314.controller.web;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

public interface AdminWebController {

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/users")
    String pageUsers(ModelMap modelMap);

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/create")
    String pageCreate(ModelMap modelMap);

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/update/{id}")
    String pageUpdate(ModelMap modelMap, @PathVariable("id") Long id);
}
