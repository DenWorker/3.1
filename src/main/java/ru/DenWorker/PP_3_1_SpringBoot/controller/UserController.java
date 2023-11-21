package ru.DenWorker.PP_3_1_SpringBoot.controller;


import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.DenWorker.PP_3_1_SpringBoot.model.User;
import ru.DenWorker.PP_3_1_SpringBoot.service.UserService;

@Controller
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping()
    public String getAllUsers(Model model) {
        model.addAttribute("listOfUsers", userService.getAllUsers());
        return "users/all_users";
    }

    @GetMapping("/new_user")
    public String newUser(@ModelAttribute("user") User user) {
        return "users/new_user";
    }

    @PostMapping("/new_user")
    public String addUser(@ModelAttribute("user") @Valid User user, BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            return "/users/new_user";
        }

        userService.addUser(user);
        return "redirect:/users";
    }

    @DeleteMapping("/delete_user")
    public String deleteUser(@RequestParam("id") Long userId) {
        userService.deleteUserById(userId);
        return "redirect:/users";
    }

    @GetMapping("/edit_user")
    public String editUser(@RequestParam("id") Long userId, Model model) {
        model.addAttribute("editUser", userService.getUserById(userId));
        return "users/edit_user";
    }

    @PatchMapping("/edit_user")
    public String patchUser(@RequestParam("id") Long userId,
                            @ModelAttribute("editUser") @Valid User updateUser,
                            BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            return "/users/edit_user";
        }

        userService.editUser(userId, updateUser);
        return "redirect:/users";
    }
}
