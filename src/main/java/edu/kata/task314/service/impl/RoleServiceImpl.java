package edu.kata.task314.service.impl;

import edu.kata.task314.entity.Role;
import edu.kata.task314.repository.RoleRepository;
import edu.kata.task314.service.RoleService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;

@AllArgsConstructor
@Service
public class RoleServiceImpl implements RoleService {

    private final RoleRepository roleRepository;

    //------------------------------------------------------------------------------------------------------------------

    @Override
    public Role findOne(Long id) {
        return roleRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException("Role not found by id: " + id)
        );
    }

    @Override
    public boolean isExistByName(String name) {
        return roleRepository.existsByName(name);
    }

    @Override
    public List<Role> findAll() {
        return roleRepository.findAll();
    }

    @Override
    public List<Role> findAll(Long userId) {
        return roleRepository.findAll(userId);
    }

    @Override
    public Role save(Role entity) {
        return roleRepository.save(entity);
    }

    @Override
    public void remove(Long id) {
        throw new RuntimeException("Not implemented");
    }
}
