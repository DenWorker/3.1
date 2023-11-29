package ru.DenWorker.PP_3_1_SpringBoot.dao;


import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.DenWorker.PP_3_1_SpringBoot.model.Role;
import ru.DenWorker.PP_3_1_SpringBoot.model.User;
import ru.DenWorker.PP_3_1_SpringBoot.service.RoleService;

import java.util.List;

@Component
public class UserDaoImp implements UserDao {

    private final RoleService roleService;
    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    public UserDaoImp(RoleService roleService) {
        this.roleService = roleService;
    }

    @Override
    public List<User> getAllUsers() {
        return entityManager.createQuery("SELECT u FROM User u", User.class).getResultList();
    }

    @Override
    public void addUser(User newUser, List<Long> roleIds) {
        if (roleIds != null) {
            List<Role> selectedRoles = roleService.getRolesByIds(roleIds);
            newUser.setRoles(selectedRoles);
        }
        entityManager.persist(newUser);
    }

    @Override
    public void deleteUserById(long userId) {
        entityManager.remove(entityManager.find(User.class, userId));
    }

    @Override
    public void editUserAndHisRoles(long userId, User updatedUser, List<Long> roleIds) {
        if (roleIds != null) {
            List<Role> selectedRoles = roleService.getRolesByIds(roleIds);
            updatedUser.setRoles(selectedRoles);
        }
        User existingUser = entityManager.find(User.class, userId);
        if (existingUser != null) {
            existingUser.setName(updatedUser.getName());
            existingUser.setGender(updatedUser.getGender());
            existingUser.setAge(updatedUser.getAge());
            existingUser.setEmail(updatedUser.getEmail());
            existingUser.setRoles(updatedUser.getRoles());
            entityManager.merge(existingUser);
        }
    }

    @Override
    public User getUserById(long userId) {
        return entityManager.find(User.class, userId);
    }
}
