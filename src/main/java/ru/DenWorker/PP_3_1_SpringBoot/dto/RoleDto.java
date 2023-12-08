package ru.DenWorker.PP_3_1_SpringBoot.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Size;
import ru.DenWorker.PP_3_1_SpringBoot.model.User;

import java.util.List;
import java.util.stream.Collectors;


public class RoleDto {
    private long id;

    @Size(min = 2, max = 100, message = "Размер роли от 2 до 100 символов!")
    private String roleName;

    private List<User> users;

    public RoleDto(long id, String roleName, List<User> users) {
        this.id = id;
        this.roleName = roleName;
        this.users = users;
    }

    public RoleDto() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }

    @JsonProperty("users")
    public List<String> getNamesOfUsersOfRole() {
        return users.stream().map(User::getName).collect(Collectors.toList());
    }
}
