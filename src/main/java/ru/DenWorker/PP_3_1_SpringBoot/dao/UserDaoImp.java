package ru.DenWorker.PP_3_1_SpringBoot.dao;


import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;
import ru.DenWorker.PP_3_1_SpringBoot.model.User;

import java.util.List;

@Repository
public class UserDaoImp implements UserDao {

    @PersistenceContext
    private EntityManager entityManager;


    @Override
    public List<User> getAllUsers() {
        return entityManager.createQuery("SELECT u FROM User u", User.class).getResultList();
    }

    @Override
    public void addUser(User newUser) {
        entityManager.persist(newUser);
    }

    @Override
    public void deleteUserById(long userId) {
        entityManager.remove(entityManager.find(User.class, userId));
    }

    @Override
    public void editUser(long userId, User updatedUser) {
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
