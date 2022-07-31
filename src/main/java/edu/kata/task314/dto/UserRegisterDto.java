package edu.kata.task314.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserRegisterDto extends UserDto {

    private String password;
    private String passwordConfirm;
}
