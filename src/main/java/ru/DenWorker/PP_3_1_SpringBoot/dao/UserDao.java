package ru.DenWorker.PP_3_1_SpringBoot.dao;


import ru.DenWorker.PP_3_1_SpringBoot.model.User;

import java.util.List;

public interface UserDao {
    List<User> getAllUsers();

    void addUser(User newUser);

    void deleteUserById(long userId);

    void editUser(long userId, User updatedUser);

    User getUserById(long userId);
}
