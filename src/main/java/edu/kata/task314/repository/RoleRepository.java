package edu.kata.task314.repository;

import edu.kata.task314.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {

    Optional<Role> findByName(String name);

    boolean existsByName(String name);

    @Query(value = "SELECT id, name FROM role r JOIN user_role ur ON ur.role_id = r.id JOIN user u ON u.id = ur.user_id WHERE u.id = :userId", nativeQuery = true)
    List<Role> findAll(@Param("userId") Long userId);
}
