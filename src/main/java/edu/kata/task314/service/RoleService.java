package edu.kata.task314.service;

import edu.kata.task314.dto.RoleDto;

import java.util.Set;

public interface RoleService {

    RoleDto findOne(Long id);

    Set<RoleDto> findAll();
}
