package ru.DenWorker.PP_3_1_SpringBoot.service;


import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.DenWorker.PP_3_1_SpringBoot.model.Role;
import ru.DenWorker.PP_3_1_SpringBoot.model.User;
import ru.DenWorker.PP_3_1_SpringBoot.repository.UsersRepository;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImp implements UserService {

    private final RoleService roleService;
    private final PasswordEncoder passwordEncoder;
    private final UsersRepository usersRepository;

    @Autowired
    public UserServiceImp(RoleService roleService, PasswordEncoder passwordEncoder, UsersRepository usersRepository) {
        this.roleService = roleService;
        this.passwordEncoder = passwordEncoder;
        this.usersRepository = usersRepository;
    }

    @Transactional(readOnly = true)
    @Override
    public List<User> getAllUsers() {
        return usersRepository.findAll();
    }

    @Transactional
    @Override
    public long addUser(User newUser, List<Long> roleIds) {
        if (!roleIds.isEmpty()) {
            List<Role> selectedRoles = roleService.getRolesByIds(roleIds);
            newUser.setRoles(selectedRoles);
        }
        newUser.setPassword(passwordEncoder.encode(newUser.getPassword()));
        User savedUser = usersRepository.save(newUser);
        return savedUser.getId();
    }

    @Transactional
    @Override
    public void deleteUserById(long userId) {
        usersRepository.deleteUserById(userId);
    }

    @Transactional
    @Override
    public void editUserAndHisRoles(long userId, User updatedUser, List<Long> rolesIds) {
        Optional<User> userOptional = usersRepository.getUserById(userId);
        if (userOptional.isPresent()) {
            User existingUser = userOptional.get();

            existingUser.setName(updatedUser.getName());
            existingUser.setGender(updatedUser.getGender());
            existingUser.setAge(updatedUser.getAge());
            existingUser.setEmail(updatedUser.getEmail());
            if (!updatedUser.getPassword().isEmpty()) {
                existingUser.setPassword(passwordEncoder.encode(updatedUser.getPassword()));
            }

            if (!rolesIds.isEmpty()) {
                List<Role> selectedRoles = roleService.getRolesByIds(rolesIds);
                existingUser.setRoles(selectedRoles);
            }

            usersRepository.save(existingUser);
        } else {
            throw new EntityNotFoundException("User with ID " + userId + " not found");
        }
    }

    @Transactional(readOnly = true)
    @Override
    public User getUserById(long userId) {
        return usersRepository.getUserById(userId).orElse(null);
    }
}
