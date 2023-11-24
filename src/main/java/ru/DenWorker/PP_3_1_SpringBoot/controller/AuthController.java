package ru.DenWorker.PP_3_1_SpringBoot.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.DenWorker.PP_3_1_SpringBoot.model.User;
import ru.DenWorker.PP_3_1_SpringBoot.security.UserDetailsImpl;
import ru.DenWorker.PP_3_1_SpringBoot.service.RoleServiceImp;
import ru.DenWorker.PP_3_1_SpringBoot.service.UserService;

import java.util.Collection;
import java.util.Collections;

@Controller
@RequestMapping("/auth")
public class AuthController {

    private final RoleServiceImp roleServiceImp;
    private final UserService userService;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public AuthController(RoleServiceImp roleServiceImp, UserService userService, PasswordEncoder passwordEncoder) {
        this.roleServiceImp = roleServiceImp;
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
    }

    @GetMapping("/login")
    String login() {
        return "/auth/login";
    }

    @GetMapping("/redirectByRole")
    public String redirectByRole(Authentication authentication) {
        if (authentication != null) {
            Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
            if (authorities.stream().anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"))) {
                return "redirect:/admin/all_users";
            } else if (authorities.stream().anyMatch(a -> a.getAuthority().equals("ROLE_USER"))) {
                return "redirect:/user/" + ((UserDetailsImpl) authentication.getPrincipal()).getUser().getId();
            }
        }
        return "redirect:/";
    }

    @GetMapping("/registration")
    String registration(@ModelAttribute("user") User user) {
        return "/auth/registration";
    }

    @PostMapping("/registration")
    public String addUser(@ModelAttribute("user") @Valid User user,
                          BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            return "/auth/registration";
        }

        user.setRoles(Collections.singletonList(roleServiceImp.findRoleByName("ROLE_USER").orElse(null)));

        user.setPassword(passwordEncoder.encode(user.getPassword()));

        userService.addUser(user);
        return "redirect:/auth/redirectByRole";
    }

}
