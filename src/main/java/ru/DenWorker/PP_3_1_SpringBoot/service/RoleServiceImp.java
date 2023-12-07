package ru.DenWorker.PP_3_1_SpringBoot.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.DenWorker.PP_3_1_SpringBoot.model.Role;
import ru.DenWorker.PP_3_1_SpringBoot.repository.RolesRepository;

import java.util.List;
import java.util.Optional;

@Service
public class RoleServiceImp implements RoleService {

    private final RolesRepository rolesRepository;

    @Autowired
    public RoleServiceImp(RolesRepository rolesRepository) {
        this.rolesRepository = rolesRepository;
    }

    @Override
    public List<Role> getAllRoles() {
        return rolesRepository.findAll();
    }

    @Override
    public List<Role> getRolesByIds(List<Long> rolesIds) {
        return rolesRepository.findByIdIn(rolesIds);
    }

    @Override
    public Optional<Role> findRoleByName(String roleName) {
        return rolesRepository.findRoleByRoleName(roleName);
    }

}
