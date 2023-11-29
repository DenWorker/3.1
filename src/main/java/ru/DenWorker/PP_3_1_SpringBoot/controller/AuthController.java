package ru.DenWorker.PP_3_1_SpringBoot.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.DenWorker.PP_3_1_SpringBoot.model.User;
import ru.DenWorker.PP_3_1_SpringBoot.security.UserDetailsImpl;
import ru.DenWorker.PP_3_1_SpringBoot.service.RoleService;
import ru.DenWorker.PP_3_1_SpringBoot.service.UserDetailsServiceImpl;
import ru.DenWorker.PP_3_1_SpringBoot.service.UserService;

@Controller
@RequestMapping("/auth")
public class AuthController {

    private final UserService userService;
    private final UserDetailsServiceImpl userDetailsService;
    private final RoleService roleService;

    @Autowired
    public AuthController(UserService userService, UserDetailsServiceImpl userDetailsService, RoleService roleService) {
        this.userService = userService;
        this.userDetailsService = userDetailsService;
        this.roleService = roleService;
    }

    @ModelAttribute("currentUser")
    public UserDetailsImpl getCurrentUser() {
        return userDetailsService.getAuthenticatedUser();
    }

    @GetMapping("/login")
    String login() {
        return "/auth/login";
    }


}
