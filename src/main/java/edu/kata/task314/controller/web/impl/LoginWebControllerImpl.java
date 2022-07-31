package edu.kata.task314.controller.web.impl;

import edu.kata.task314.controller.web.LoginWebController;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping
public class LoginWebControllerImpl implements LoginWebController {

    @Override
    public String index() {
        return "redirect:/login";
    }

    @Override
    public String login() {
        return "login";
    }

    @Override
    public String loginError(ModelMap modelMap) {
        modelMap.addAttribute("loginError", true);
        return "login";
    }
}
