package edu.kata.task314.config;

import edu.kata.task314.entity.Role;
import edu.kata.task314.entity.User;
import edu.kata.task314.service.RoleService;
import edu.kata.task314.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;

@AllArgsConstructor
@Configuration
public class DbInitConfig {

    private final UserService userService;
    private final RoleService roleService;

    //------------------------------------------------------------------------------------------------------------------

    @PostConstruct
    private void createUsers() {

        Role roleAdmin = getRole("admin");
        if (!roleService.isExistByName(roleAdmin.getName())) {
            roleService.save(roleAdmin);
        }

        Role roleUser = getRole("user");
        if (!roleService.isExistByName(roleUser.getName())) {
            roleService.save(roleUser);
        }

        User userAdmin = getUser("admin");
        if (!userService.isExistByLogin(userAdmin.getLogin())) {
            userAdmin = userService.save(userAdmin);
        }
        if (userService.findOne(userAdmin.getLogin()).getRoles().isEmpty()) {
            userAdmin.setRoles(new HashSet<>(Arrays.asList(roleAdmin, roleUser)));
            userService.save(userAdmin);
        }

        User userUser = getUser("user");
        if (!userService.isExistByLogin(userUser.getLogin())) {
            userUser = userService.save(userUser);
        }
        if (userService.findOne(userUser.getLogin()).getRoles().isEmpty()) {
            userUser.setRoles(new HashSet<>(Collections.singletonList(roleUser)));
            userService.save(userUser);
        }
    }

    private Role getRole(String name) {
        Role role = new Role();
        role.setName(name.toUpperCase());
        return role;
    }

    private User getUser(String name) {
        User user = new User();
        user.setLogin(name + "@localhost");
        user.setPassword(name);
        user.setName(name);
        user.setLastName(name + " last_name");
        user.setMiddleName(name + " middle_name");
        user.setAccountNonLocked(true);
        user.setAccountNonExpired(true);
        user.setCredentialsNonExpired(true);
        user.setEnabled(true);
        return user;
    }
}