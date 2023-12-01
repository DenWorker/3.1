package ru.DenWorker.PP_3_1_SpringBoot.dao;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.DenWorker.PP_3_1_SpringBoot.model.User;
import ru.DenWorker.PP_3_1_SpringBoot.repository.UsersRepository;

import java.util.List;
import java.util.Optional;

@Component
public class UserDaoImp implements UserDao {

    private final UsersRepository usersRepository;

    @Autowired
    public UserDaoImp(UsersRepository usersRepository) {
        this.usersRepository = usersRepository;
    }

    @Override
    public List<User> getAllUsers() {
        return usersRepository.findAll();
    }

    @Override
    public void addUser(User newUser) {
        usersRepository.save(newUser);
    }

    @Override
    public void deleteUserById(long userId) {
        usersRepository.deleteById(userId);
    }

    @Override
    public void editUserAndHisRoles(User updatedUser) {
        usersRepository.save(updatedUser);
    }


    @Override
    public Optional<User> getUserById(long userId) {
        return usersRepository.getUserById(userId);
    }
}
