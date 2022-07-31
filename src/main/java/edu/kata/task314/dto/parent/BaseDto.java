package edu.kata.task314.dto.parent;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public abstract class BaseDto implements Serializable {

    private Long id;
}
