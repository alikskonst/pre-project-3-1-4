package edu.kata.task314.service;

import edu.kata.task314.entity.Role;

import java.util.List;

public interface RoleService extends CommonService<Role> {

    boolean isExistByName(String name);

    List<Role> findAll(Long userId);
}
