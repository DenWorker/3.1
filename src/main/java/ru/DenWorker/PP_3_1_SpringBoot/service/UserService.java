package ru.DenWorker.PP_3_1_SpringBoot.service;


import ru.DenWorker.PP_3_1_SpringBoot.model.User;

import java.util.List;

public interface UserService {
    List<User> getAllUsers();

    long addUser(User newUser, List<Long> roleIds);

    void deleteUserById(long userId);

    void editUserAndHisRoles(long userId, User updatedUser, List<Long> rolesIds);

    User getUserById(long userId);

}
