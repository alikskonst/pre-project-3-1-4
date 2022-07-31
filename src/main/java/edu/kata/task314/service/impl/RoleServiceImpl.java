package edu.kata.task314.service.impl;

import edu.kata.task314.converter.RoleConverter;
import edu.kata.task314.dto.RoleDto;
import edu.kata.task314.entity.Role;
import edu.kata.task314.repository.RoleRepository;
import edu.kata.task314.service.RoleService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@AllArgsConstructor
@Service
@Transactional
public class RoleServiceImpl implements RoleService {

    private final RoleRepository roleRepository;
    private final RoleConverter roleConverter;

    //------------------------------------------------------------------------------------------------------------------

    @Override
    public RoleDto findOne(Long id) {
        Role role = roleRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException("Role not found by id: " + id)
        );
        return roleConverter.convert(role);
    }

    @Override
    public Set<RoleDto> findAll() {
        List<Role> roleList = roleRepository.findAll();
        return roleList.isEmpty() ?
                Collections.emptySet() :
                roleList.stream().map(roleConverter::convert).collect(Collectors.toSet());
    }
}
