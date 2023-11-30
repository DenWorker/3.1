package ru.DenWorker.PP_3_1_SpringBoot.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.DenWorker.PP_3_1_SpringBoot.security.UserDetailsImpl;
import ru.DenWorker.PP_3_1_SpringBoot.service.UserDetailsServiceImpl;

@Controller
@RequestMapping("/auth")
public class AuthController {

    private final UserDetailsServiceImpl userDetailsService;

    @Autowired
    public AuthController(UserDetailsServiceImpl userDetailsService) {
        this.userDetailsService = userDetailsService;
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
