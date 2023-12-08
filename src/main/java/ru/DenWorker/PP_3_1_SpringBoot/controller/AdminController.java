package ru.DenWorker.PP_3_1_SpringBoot.controller;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin")
public class AdminController {

    @GetMapping("/all_users")
    public String getAllUsers() {
        return "users/all_users";
    }
}
