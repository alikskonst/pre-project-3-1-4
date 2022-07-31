package edu.kata.task314.service.impl;

import edu.kata.task314.converter.UserConverter;
import edu.kata.task314.dto.UserDto;
import edu.kata.task314.dto.UserRegisterDto;
import edu.kata.task314.entity.User;
import edu.kata.task314.repository.UserRepository;
import edu.kata.task314.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@Service
@Transactional
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserConverter userConverter;
    private final PasswordEncoder passwordEncoder;

    //------------------------------------------------------------------------------------------------------------------

    @Override
    public UserDto findOne(Long id) {
        User user = userRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException("User not found by id: " + id)
        );
        return userConverter.convert(user);
    }

    @Override
    public UserDto findOne(String login) {
        User user = userRepository.findOneByLogin(login).orElseThrow(
                () -> new EntityNotFoundException("User not found by login: " + login)
        );
        return userConverter.convert(user);
    }

    @Override
    public List<UserDto> findAll() {
        List<User> userList = userRepository.findAll();
        return userList.isEmpty() ?
                Collections.emptyList() :
                userList.stream().map(userConverter::convert).collect(Collectors.toList());
    }

    @Override
    public UserDto save(UserDto userDto) {
        return userConverter.convert(userRepository.save(userConverter.convert(userDto)));
    }

    @Override
    public UserDto save(UserRegisterDto userRegisterDto) {
        if (!userRegisterDto.getPassword().equalsIgnoreCase(userRegisterDto.getPasswordConfirm())) {
            throw new RuntimeException("чото там про валидацию паролей");
        }
        User user = userConverter.convert(userRegisterDto);
        user.setPassword(passwordEncoder.encode(userRegisterDto.getPassword()));
        return userConverter.convert(userRepository.save(user));
    }

    @Override
    public void remove(Long id) {
        userRepository.deleteById(id);
    }
}
