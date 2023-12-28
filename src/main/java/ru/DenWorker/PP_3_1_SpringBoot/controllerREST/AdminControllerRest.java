package ru.DenWorker.PP_3_1_SpringBoot.controllerREST;


import com.fasterxml.jackson.databind.ObjectMapper;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.DenWorker.PP_3_1_SpringBoot.dto.RoleDto;
import ru.DenWorker.PP_3_1_SpringBoot.dto.UserDto;
import ru.DenWorker.PP_3_1_SpringBoot.model.Role;
import ru.DenWorker.PP_3_1_SpringBoot.model.User;
import ru.DenWorker.PP_3_1_SpringBoot.service.RoleService;
import ru.DenWorker.PP_3_1_SpringBoot.service.UserService;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/admin")
public class AdminControllerRest {

    private final UserService userService;
    private final RoleService roleService;
    private final ModelMapper modelMapper;
    private final ObjectMapper objectMapper;

    @Autowired
    public AdminControllerRest(UserService userService, RoleService roleService, ModelMapper modelMapper, ObjectMapper objectMapper) {
        this.userService = userService;
        this.roleService = roleService;
        this.modelMapper = modelMapper;
        this.objectMapper = objectMapper;
    }

    @GetMapping("/get_all_roles")
    public List<RoleDto> getAllRoles() {
        return roleService.getAllRoles().stream().map(this::convertToRoleDto).toList();
    }

    @GetMapping("/get_all_users")
    public List<UserDto> getAllRole() {
        return userService.getAllUsers().stream().map(this::convertToUserDto).toList();
    }

    @PostMapping("/create_user")
    public long addUser(@RequestBody Map<String, Object> requestBody) {
        User newUser = objectMapper.convertValue(requestBody.get("newUser"), User.class);

        List<Long> rolesIds = ((List<?>) requestBody.get("rolesIds")).stream()
                .map(Object::toString)
                .map(Long::parseLong)
                .toList();
        return userService.addUser(newUser, rolesIds);
    }

    @GetMapping("/delete_user/{id}")
    public UserDto getDeleteUserDto(@PathVariable("id") long userId) {
        return convertToUserDto(userService.getUserById(userId));
    }

    @DeleteMapping("/delete_user/{id}")
    public void deleteUser(@PathVariable("id") long userId) {
        userService.deleteUserById(userId);
    }

    @GetMapping("/edit_user/{id}")
    public UserDto getEditUserDto(@PathVariable("id") long userId) {
        return convertToUserDto(userService.getUserById(userId));
    }

    @PatchMapping("/edit_user/{id}")
    public void editUser(@PathVariable("id") long userId, @RequestBody Map<String, Object> requestBody) {
        User updatedUser = objectMapper.convertValue(requestBody.get("updatedUser"), User.class);

        List<Long> rolesIds = ((List<?>) requestBody.get("rolesIds")).stream()
                .map(Object::toString)
                .map(Long::parseLong)
                .toList();

        userService.editUserAndHisRoles(userId, updatedUser, rolesIds);
    }

    private User convertToUser(UserDto userDto) {
        return modelMapper.map(userDto, User.class);
    }

    private UserDto convertToUserDto(User user) {
        return modelMapper.map(user, UserDto.class);
    }

    private Role convertToRole(RoleDto roleDto) {
        return modelMapper.map(roleDto, Role.class);
    }

    private RoleDto convertToRoleDto(Role role) {
        return modelMapper.map(role, RoleDto.class);
    }

}
