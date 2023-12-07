package ru.DenWorker.PP_3_1_SpringBoot.service;

import ru.DenWorker.PP_3_1_SpringBoot.model.Role;

import java.util.List;
import java.util.Optional;

public interface RoleService {
    List<Role> getAllRoles();

    List<Role> getRolesByIds(List<Long> rolesIds);

    Optional<Role> findRoleByName(String roleName);
}
