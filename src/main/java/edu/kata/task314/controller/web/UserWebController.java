package edu.kata.task314.controller.web;

import edu.kata.task314.entity.User;
import edu.kata.task314.facade.UserFacade;
import lombok.AllArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@AllArgsConstructor
@Controller
@RequestMapping
public class UserWebController {

    private final UserFacade userFacade;

    //------------------------------------------------------------------------------------------------------------------

    @PreAuthorize("hasRole = 'ADMIN'")
    @GetMapping("/admin")
    public String admin(ModelMap modelMap) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        modelMap.addAttribute("me", userFacade.findOneUser(user.getLogin()));
        return "admin";
    }

    @PreAuthorize("hasAnyRole = {'USER', 'ADMIN'}")
    @GetMapping("/user")
    public String user(ModelMap modelMap) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        modelMap.addAttribute("me", userFacade.findOneUser(user.getLogin()));
        return "user";
    }
}
