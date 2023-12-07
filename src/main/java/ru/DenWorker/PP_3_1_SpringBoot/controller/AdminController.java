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
import ru.DenWorker.PP_3_1_SpringBoot.service.RoleService;
import ru.DenWorker.PP_3_1_SpringBoot.service.UserService;

import java.util.List;

@Controller
@RequestMapping("/admin")
public class AdminController {

    private final UserService userService;
    private final RoleService roleService;

    @Autowired
    public AdminController(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }

    @ModelAttribute("currentUser")
    public UserDetailsImpl getCurrentUser() {
        return userService.getAuthenticatedUser();
    }

    @ModelAttribute("listOfUsers")
    public List<User> getListOfUsers() {
        return userService.getAllUsers();
    }

    @ModelAttribute("roles")
    public List<Role> getListOfRoles() {
        return roleService.getAllRoles();
    }

    @ModelAttribute("newUser")
    public User getNewUser() {
        return new User();
    }

    @GetMapping("/all_users")
    public String getAllUsers() {

        if (false) {
            return "other/navbar";
        }

        return "users/all_users";
    }


    @GetMapping("/delete_user/{id}")
    public String deleteUser(@PathVariable("id") Long userId,
                             @ModelAttribute("deleteUser") User deleteUser,
                             Model model) {
        model.addAttribute("deleteUser", userService.getUserById(userId));
        return "/users/all_users";
    }

    @DeleteMapping("/delete_user/{id}")
    public String deleteUser(@PathVariable("id") Long userId) {
        userService.deleteUserById(userId);
        return "redirect:/admin/all_users";
    }

    @PostMapping("/create_user")
    public String addUser(@ModelAttribute("newUser") @Valid User newUser,
                          BindingResult bindingResult,
                          @RequestParam(name = "roles", required = false) List<Long> roleIds) {

        if (bindingResult.hasErrors()) {
            return "/users/all_users";
        }

        userService.addUser(newUser, roleIds);
        return "redirect:/admin/all_users";
    }

    @GetMapping("/edit_user/{id}")
    public String editUser(@PathVariable("id") Long userId,
                           @ModelAttribute("editUser") User editUser,
                           Model model) {
        model.addAttribute("editUser", userService.getUserById(userId));
        return "/users/all_users";
    }


    @PatchMapping("/edit_user/{id}")
    public String patchUser(@PathVariable("id") Long userId,
                            @ModelAttribute("editUser") @Valid User editUser,
                            BindingResult bindingResult,
                            @RequestParam(name = "roles", required = false) List<Long> roleIds) {

        if (bindingResult.hasErrors()) {
            return "/users/all_users";
        }

        userService.editUserAndHisRoles(userId, editUser, roleIds);
        return "redirect:/admin/all_users";
    }
}
