package edu.kata.task314.dto;

import edu.kata.task314.dto.parent.NameDto;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;

@Getter
@Setter
public class UserDto extends NameDto {

    private String lastName;
    private String login;
    private boolean accountNonExpired;
    private boolean accountNonLocked;
    private boolean credentialsNonExpired;
    private boolean enabled;
}
