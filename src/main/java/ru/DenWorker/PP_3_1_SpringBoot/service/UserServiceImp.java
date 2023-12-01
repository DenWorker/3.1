package ru.DenWorker.PP_3_1_SpringBoot.service;


import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.DenWorker.PP_3_1_SpringBoot.dao.UserDao;
import ru.DenWorker.PP_3_1_SpringBoot.model.Role;
import ru.DenWorker.PP_3_1_SpringBoot.model.User;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImp implements UserService {

    private final RoleService roleService;
    private final PasswordEncoder passwordEncoder;
    private final UserDao userDao;

    @Autowired
    public UserServiceImp(RoleService roleService, PasswordEncoder passwordEncoder, UserDao userDao) {
        this.roleService = roleService;
        this.passwordEncoder = passwordEncoder;
        this.userDao = userDao;
    }

    @Transactional(readOnly = true)
    @Override
    public List<User> getAllUsers() {
        return userDao.getAllUsers();
    }

    @Transactional
    @Override
    public void addUser(User newUser, List<Long> roleIds) {
        if (roleIds != null) {
            List<Role> selectedRoles = roleService.getRolesByIds(roleIds);
            newUser.setRoles(selectedRoles);
        }
        newUser.setPassword(passwordEncoder.encode(newUser.getPassword()));
        userDao.addUser(newUser);
    }

    @Transactional
    @Override
    public void deleteUserById(long userId) {
        userDao.deleteUserById(userId);
    }

    @Transactional
    @Override
    public void editUserAndHisRoles(long userId, User updatedUser, List<Long> roleIds) {
        Optional<User> userOptional = userDao.getUserById(userId);
        if (userOptional.isPresent()) {
            User existingUser = userOptional.get();

            existingUser.setName(updatedUser.getName());
            existingUser.setGender(updatedUser.getGender());
            existingUser.setAge(updatedUser.getAge());
            existingUser.setEmail(updatedUser.getEmail());
            if (!updatedUser.getPassword().isEmpty()) {
                existingUser.setPassword(passwordEncoder.encode(updatedUser.getPassword()));
            }

            if (roleIds != null) {
                List<Role> selectedRoles = roleService.getRolesByIds(roleIds);
                existingUser.setRoles(selectedRoles);
            }

            userDao.editUserAndHisRoles(existingUser);
        } else {
            throw new EntityNotFoundException("User with ID " + userId + " not found");
        }
    }

    @Transactional(readOnly = true)
    @Override
    public User getUserById(long userId) {
        return userDao.getUserById(userId).orElse(null);
    }
}
