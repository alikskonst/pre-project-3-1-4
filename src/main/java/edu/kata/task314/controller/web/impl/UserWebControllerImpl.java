package edu.kata.task314.controller.web.impl;

import edu.kata.task314.controller.web.UserWebController;
import edu.kata.task314.entity.User;
import edu.kata.task314.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

@AllArgsConstructor
@Controller
@RequestMapping("/user")
public class UserWebControllerImpl implements UserWebController {

    private final UserService userService;

    //------------------------------------------------------------------------------------------------------------------

    @Override
    public String userPage(ModelMap modelMap) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        modelMap.addAttribute("currentUser", userService.findOne(user.getLogin()));
        return "user_info";
    }
}
