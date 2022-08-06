package edu.kata.task314.controller.web;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;

public interface AdminWebController {

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping
    String pageUsers(ModelMap modelMap);
}
