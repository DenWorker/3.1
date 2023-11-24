package ru.DenWorker.PP_3_1_SpringBoot.controller;


import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.DenWorker.PP_3_1_SpringBoot.model.Role;
import ru.DenWorker.PP_3_1_SpringBoot.model.User;
import ru.DenWorker.PP_3_1_SpringBoot.security.UserDetailsImpl;
import ru.DenWorker.PP_3_1_SpringBoot.service.RoleServiceImp;
import ru.DenWorker.PP_3_1_SpringBoot.service.UserDetailsServiceImpl;
import ru.DenWorker.PP_3_1_SpringBoot.service.UserService;

import java.util.List;

@Controller
@RequestMapping("/admin")
public class AdminController {

    private final UserService userService;
    private final UserDetailsServiceImpl userDetailsService;
    private final RoleServiceImp roleServiceImp;

    @Autowired
    public AdminController(UserService userService, UserDetailsServiceImpl userDetailsService, RoleServiceImp roleServiceImp) {
        this.userService = userService;
        this.userDetailsService = userDetailsService;
        this.roleServiceImp = roleServiceImp;
    }

    @ModelAttribute("currentUser")
    public UserDetailsImpl getCurrentUser() {
        return userDetailsService.getAuthenticatedUser();
    }

    @GetMapping("/all_users")
    public String getAllUsers(Model model) {
        model.addAttribute("listOfUsers", userService.getAllUsers());

        if (false) {
            return "other/navbar";
        }

        return "users/all_users";
    }
    

    @DeleteMapping("/delete_user")
    public String deleteUser(@RequestParam("id") Long userId) {
        userService.deleteUserById(userId);
        return "redirect:/admin/all_users";
    }

    @GetMapping("/edit_user")
    public String editUser(@RequestParam("id") Long userId, Model model) {
        model.addAttribute("editUser", userService.getUserById(userId));
        model.addAttribute("roles", roleServiceImp.getAllRoles());
        return "users/edit_user";
    }

    @PatchMapping("/edit_user")
    public String patchUser(@RequestParam("id") Long userId,
                            @ModelAttribute("editUser") @Valid User updateUser,
                            BindingResult bindingResult,
                            @RequestParam(name = "roles", required = false) List<Long> roleIds) {

        if (bindingResult.hasErrors()) {
            return "/users/edit_user";
        }

        if (roleIds != null) {
            List<Role> selectedRoles = roleServiceImp.getRolesByIds(roleIds);
            updateUser.setRoles(selectedRoles);
        }

        userService.editUser(userId, updateUser);
        return "redirect:/admin/all_users";
    }
}
