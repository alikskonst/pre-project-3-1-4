package edu.kata.task314.controller.web;

import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;

public interface LoginWebController {

    @GetMapping
    String index();

    @GetMapping("/login")
    String login();

    @GetMapping("/loginError")
    String loginError(ModelMap modelMap);
}
