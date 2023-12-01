package ru.DenWorker.PP_3_1_SpringBoot.dao;


import ru.DenWorker.PP_3_1_SpringBoot.model.User;

import java.util.List;
import java.util.Optional;

public interface UserDao {
    List<User> getAllUsers();

    void addUser(User newUser);

    void deleteUserById(long userId);

    void editUserAndHisRoles(User updatedUser);

    Optional<User> getUserById(long userId);
}
