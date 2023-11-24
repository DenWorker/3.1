package ru.DenWorker.PP_3_1_SpringBoot.model;


import jakarta.persistence.*;
import jakarta.validation.constraints.*;

import java.util.List;
import java.util.stream.Collectors;

@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "name")
    @Size(min = 6, max = 300, message = "Размер имени от 6 до 300 символов!")
    private String name;

    @Column(name = "gender")
    @NotEmpty(message = "Пол не может быть пустым!")
    private String gender;

    @Column(name = "age")
    @Min(value = 0, message = "Возраст не может быть меньше 0 лет!")
    @Max(value = 127, message = "Возраст не может быть больше 127 лет!")
    private byte age;

    @Column(name = "email")
    @Email(message = "Неверно введён адрес электронной почты!")
    private String email;

    @Column(name = "password")
    @Size(min = 6, max = 300, message = "Размер пароля от 6 до 300 символов!")
    private String password;

    @ManyToMany
    @JoinTable(
            name = "users_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private List<Role> roles;

    public User(long id, String name, String gender, byte age, String email, String password, List<Role> roles) {
        this.id = id;
        this.name = name;
        this.gender = gender;
        this.age = age;
        this.email = email;
        this.password = password;
        this.roles = roles;
    }

    public User() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public byte getAge() {
        return age;
    }

    public void setAge(byte age) {
        this.age = age;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<Role> getRoles() {
        return roles;
    }

    public void setRoles(List<Role> roles) {
        this.roles = roles;
    }

    public String getStringRoles() {
        return getRoles().stream().map(Role::getRoleName).collect(Collectors.joining(";  "));
    }
}
