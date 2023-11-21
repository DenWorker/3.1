package ru.DenWorker.PP_3_1_SpringBoot.service;


import ru.DenWorker.PP_3_1_SpringBoot.model.User;

import java.util.List;

public interface UserService {
    List<User> getAllUsers();

    void addUser(User newUser);

    void deleteUserById(long userId);

    void editUser(long userId, User updatedUser);

    User getUserById(long userId);

}
