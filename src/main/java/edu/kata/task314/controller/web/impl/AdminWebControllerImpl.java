package edu.kata.task314.controller.web.impl;

import edu.kata.task314.controller.web.AdminWebController;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

//@AllArgsConstructor
@Controller
@RequestMapping("/admin")
public class AdminWebControllerImpl implements AdminWebController {

//    private final UserService userService;

    //------------------------------------------------------------------------------------------------------------------

    @Override
    public String pageUsers(ModelMap modelMap) {
//        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
//        modelMap.addAttribute("currentUser", userService.findOne(user.getLogin()));
//        modelMap.addAttribute("user", new User());
//        modelMap.addAttribute("userList", userService.findAll());
        return "control_panel";
    }
}
