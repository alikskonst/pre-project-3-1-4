package edu.kata.task314.service;

import edu.kata.task314.dto.RoleDto;

import java.util.List;

public interface RoleService {

    RoleDto findOne(Long id);

    List<RoleDto> findAll();
}
