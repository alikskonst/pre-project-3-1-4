package edu.kata.task314.service;

import edu.kata.task314.entity.Role;

import java.util.List;

public interface RoleService extends CommonService<Role> {

    List<Role> findAll(Long userId);
}
