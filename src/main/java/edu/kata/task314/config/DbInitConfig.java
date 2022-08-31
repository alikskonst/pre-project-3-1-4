package edu.kata.task314.config;

import edu.kata.task314.entity.Role;
import edu.kata.task314.entity.User;
import edu.kata.task314.repository.RoleRepository;
import edu.kata.task314.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Optional;

@AllArgsConstructor
@Configuration
public class DbInitConfig {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    //------------------------------------------------------------------------------------------------------------------

    @PostConstruct
    private void createUsers() {

        Role roleAdmin = getRole("admin");
        if (!roleRepository.existsByName(roleAdmin.getName())) {
            roleRepository.save(roleAdmin);
        }

        Role roleUser = getRole("user");
        if (!roleRepository.existsByName(roleUser.getName())) {
            roleRepository.save(roleUser);
        }

        User userAdmin = getUser("admin", "$2a$10$nKBrTHS7jICOPqCq22Uc9u7AOUtaP7dM4S8BmlfNrvHvmXdd9L3LO");
        if (!userRepository.existsByLogin(userAdmin.getLogin())) {
            userAdmin = userRepository.save(userAdmin);
        }
        Optional<User> userAdminOptional = userRepository.findByLogin(userAdmin.getLogin());
        if (userAdminOptional.isPresent()) {
            userAdmin = userAdminOptional.get();
            if (userAdmin.getRoles().isEmpty()) {
                userAdmin.setRoles(new HashSet<>(Arrays.asList(roleAdmin, roleUser)));
                userRepository.save(userAdmin);
            }
        }

        User userUser = getUser("user", "$2a$10$7yJM9vrlytBzARxGjc5daOwAlEdSPHa4fJmEUVsGhWExw5QdAJQ2i");
        if (!userRepository.existsByLogin(userUser.getLogin())) {
            userUser = userRepository.save(userUser);
        }
        Optional<User> userUserOptional = userRepository.findByLogin(userUser.getLogin());
        if (userUserOptional.isPresent()) {
            userUser = userUserOptional.get();
            if (userUser.getRoles().isEmpty()) {
                userUser.setRoles(new HashSet<>(Collections.singletonList(roleUser)));
                userRepository.save(userUser);
            }
        }
    }

    private Role getRole(String name) {
        Role role = new Role();
        role.setName(name.toUpperCase());
        return role;
    }

    private User getUser(String name, String password) {
        User user = new User();
        user.setLogin(name + "@localhost");
        user.setPassword(password);
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