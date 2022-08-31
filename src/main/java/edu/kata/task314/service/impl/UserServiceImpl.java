package edu.kata.task314.service.impl;

import edu.kata.task314.entity.User;
import edu.kata.task314.repository.UserRepository;
import edu.kata.task314.service.UserService;
import edu.kata.task314.util.StringUtils;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import javax.persistence.EntityNotFoundException;
import java.util.List;

@AllArgsConstructor
@Component
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    //------------------------------------------------------------------------------------------------------------------

    @Override
    public User findOne(Long id) {
        return userRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException("User not found by id: " + id)
        );
    }

    @Override
    public User findOne(String login) {
        return userRepository.findByLogin(login).orElseThrow(
                () -> new EntityNotFoundException("User not found by login: " + login)
        );
    }

    @Override
    public boolean isExistByLogin(String login) {
        return userRepository.existsByLogin(login);
    }

    @Override
    public List<User> findAll() {
        return userRepository.findAll();
    }

    @Override
    public User save(User entity) {
        User savedUser = entity.getId() == null ? new User() : findOne(entity.getId());
        entity.setPassword(
                StringUtils.isNotEmpty(entity.getPassword()) ?
                        passwordEncoder.encode(entity.getPassword()) :
                        savedUser.getPassword()
        );
        entity.setAccountNonExpired(true);
        entity.setAccountNonLocked(true);
        entity.setCredentialsNonExpired(true);
        entity.setEnabled(true);
        return userRepository.save(entity);
    }

    @Override
    public void remove(Long id) {
        userRepository.deleteById(id);
    }
}
