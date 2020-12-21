package ru.springcourse.models;

import javax.validation.constraints.Email;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

public class Person {
    private int id;
    @NotEmpty(message = "Name should not be empty")
    @Size(max = 30,min = 1,message = "Name should be between 1 and 30 characters")
    private String name;
    @NotEmpty(message = "Email should not be empty")
    @Email(message = "Email should be valid")
    private String email;
    @Min(value = 1, message = "Age should be greater than 0")
    private int age;

    public Person() {

    }

    public Person(int id, @NotEmpty(message = "Please enter the name") @Size(max = 30, min = 1, message = "Name must contain 1-30 characters") String name, @NotEmpty(message = "Please enter an email") @Email String email, @NotEmpty(message = "Please enter the age") @Size(max = 3, min = 1, message = "Age can be 3 symbols long") int age) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.age = age;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public int getAge() {
        return age;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setAge(int age) {
        this.age = age;
    }
}
