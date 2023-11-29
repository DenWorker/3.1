package ru.DenWorker.PP_3_1_SpringBoot.controllerREST;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.DenWorker.PP_3_1_SpringBoot.dao.UserDao;
import ru.DenWorker.PP_3_1_SpringBoot.model.User;

@RestController
@RequestMapping("/api/admin")
public class AdminControllerRest {

    private final UserDao userDao;

    @Autowired
    public AdminControllerRest(UserDao userDao) {
        this.userDao = userDao;
    }

    @GetMapping("/user/{id}")
    public User getUserById(@PathVariable("id") long id) {
        return userDao.getUserById(id);
    }
}
