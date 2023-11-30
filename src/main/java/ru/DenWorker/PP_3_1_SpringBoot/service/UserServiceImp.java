package ru.DenWorker.PP_3_1_SpringBoot.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.DenWorker.PP_3_1_SpringBoot.dao.UserDao;
import ru.DenWorker.PP_3_1_SpringBoot.model.User;

import java.util.List;

@Service
public class UserServiceImp implements UserService {

    private final UserDao userDao;

    @Autowired
    public UserServiceImp(UserDao userDao) {
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
        userDao.addUser(newUser, roleIds);
    }

    @Transactional
    @Override
    public void deleteUserById(long userId) {
        userDao.deleteUserById(userId);
    }

    @Transactional
    @Override
    public void editUserAndHisRoles(long userId, User updatedUser, List<Long> roleIds) {
        userDao.editUserAndHisRoles(userId, updatedUser, roleIds);
    }

    @Transactional(readOnly = true)
    @Override
    public User getUserById(long userId) {
        return userDao.getUserById(userId);
    }
}
