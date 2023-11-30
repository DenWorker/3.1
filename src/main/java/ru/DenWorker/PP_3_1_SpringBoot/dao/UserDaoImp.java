package ru.DenWorker.PP_3_1_SpringBoot.dao;


import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import ru.DenWorker.PP_3_1_SpringBoot.model.Role;
import ru.DenWorker.PP_3_1_SpringBoot.model.User;
import ru.DenWorker.PP_3_1_SpringBoot.repository.UsersRepository;
import ru.DenWorker.PP_3_1_SpringBoot.service.RoleService;

import java.util.List;
import java.util.Optional;

@Component
public class UserDaoImp implements UserDao {

    private final RoleService roleService;
    private final UsersRepository usersRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserDaoImp(RoleService roleService, UsersRepository usersRepository, PasswordEncoder passwordEncoder) {
        this.roleService = roleService;
        this.usersRepository = usersRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public List<User> getAllUsers() {
        return usersRepository.findAll();
    }

    @Override
    public void addUser(User newUser, List<Long> roleIds) {
        if (roleIds != null) {
            List<Role> selectedRoles = roleService.getRolesByIds(roleIds);
            newUser.setRoles(selectedRoles);
        }
        newUser.setPassword(passwordEncoder.encode(newUser.getPassword()));
        usersRepository.save(newUser);
    }

    @Override
    public void deleteUserById(long userId) {
        usersRepository.deleteById(userId);
    }

    @Override
    public void editUserAndHisRoles(long userId, User updatedUser, List<Long> roleIds) {
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


            if (roleIds != null) {
                List<Role> selectedRoles = roleService.getRolesByIds(roleIds);
                existingUser.setRoles(selectedRoles);
            }

            usersRepository.save(existingUser);
        } else {
            throw new EntityNotFoundException("User with ID " + userId + " not found");
        }
    }


    @Override
    public User getUserById(long userId) {
        return usersRepository.getUserById(userId).orElse(null);
    }
}
