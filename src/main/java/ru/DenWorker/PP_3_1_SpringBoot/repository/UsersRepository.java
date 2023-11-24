package ru.DenWorker.PP_3_1_SpringBoot.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.DenWorker.PP_3_1_SpringBoot.model.User;

import java.util.Optional;

@Repository
public interface UsersRepository extends JpaRepository<User, Integer> {
    Optional<User> getUserByName(String userName);
}
