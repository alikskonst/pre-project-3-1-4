package edu.kata.task314.entity.parent;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;

@Getter
@Setter
@MappedSuperclass
public abstract class NameEntity extends BaseEntity {

    @Column(name = "name")
    private String name;
}
