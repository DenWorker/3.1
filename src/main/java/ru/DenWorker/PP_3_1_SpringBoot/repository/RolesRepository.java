package ru.DenWorker.PP_3_1_SpringBoot.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.DenWorker.PP_3_1_SpringBoot.model.Role;

import java.util.List;
import java.util.Optional;

@Repository
public interface RolesRepository extends JpaRepository<Role, Integer> {
    List<Role> findByIdIn(List<Long> roleIds);

    Optional<Role> findRoleByRoleName(String roleName);
}