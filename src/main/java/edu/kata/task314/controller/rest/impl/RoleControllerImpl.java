package edu.kata.task314.controller.rest.impl;

import edu.kata.task314.controller.rest.RoleController;
import edu.kata.task314.dto.RoleDto;
import edu.kata.task314.service.RoleService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/api/roles")
public class RoleControllerImpl implements RoleController {

    private final RoleService roleService;

    //------------------------------------------------------------------------------------------------------------------

    @Override
    public RoleDto findOne(Long id) {
        return roleService.findOne(id);
    }

    @Override
    public List<RoleDto> findAll() {
        return roleService.findAll();
    }
}
