package ru.DenWorker.PP_3_1_SpringBoot.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.DenWorker.PP_3_1_SpringBoot.security.UserDetailsImpl;
import ru.DenWorker.PP_3_1_SpringBoot.service.UserDetailsServiceImpl;

@Controller
@RequestMapping("/user")
public class UserController {
    private final UserDetailsServiceImpl userDetailsService;

    @Autowired
    public UserController(UserDetailsServiceImpl userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    @ModelAttribute("currentUser")
    public UserDetailsImpl getCurrentUser() {
        return userDetailsService.getAuthenticatedUser();
    }

    @GetMapping("/{}")
    public String showUser(Model model) {
        model.addAttribute("currentUser", userDetailsService.getAuthenticatedUser());
        return "users/all_users";
    }
}
