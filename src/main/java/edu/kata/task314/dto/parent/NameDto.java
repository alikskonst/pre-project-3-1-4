package edu.kata.task314.dto.parent;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
public abstract class NameDto extends BaseDto {

    @NotNull
    private String name;
}
