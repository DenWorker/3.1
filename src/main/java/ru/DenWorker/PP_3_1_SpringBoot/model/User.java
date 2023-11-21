package ru.DenWorker.PP_3_1_SpringBoot.model;


import jakarta.persistence.*;
import jakarta.validation.constraints.*;

@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "name")
    @Size(min = 2, max = 30, message = "Размер имени от 2 до 30 символов!")
    private String name;

    @Column(name = "last_name")
    @Size(min = 2, max = 30, message = "Размер отчества от 2 до 30 символов!")
    private String last_name;

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

    public User(String name, String last_name, String gender, byte age, String email) {
        this.name = name;
        this.last_name = last_name;
        this.gender = gender;
        this.age = age;
        this.email = email;
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

    public String getLast_name() {
        return last_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
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
}
