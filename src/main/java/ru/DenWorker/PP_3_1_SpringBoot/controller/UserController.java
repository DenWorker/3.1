package ru.DenWorker.PP_3_1_SpringBoot.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.DenWorker.PP_3_1_SpringBoot.security.UserDetailsImpl;
import ru.DenWorker.PP_3_1_SpringBoot.service.UserService;

@Controller
@RequestMapping("/user")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @ModelAttribute("currentUser")
    public UserDetailsImpl getCurrentUser() {
        return userService.getAuthenticatedUser();
    }

    @GetMapping("/{}")
    public String showUser() {
        return "users/all_users";
    }
}
