package ru.makan1.petclinic.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;

import java.util.ArrayList;
import java.util.List;

public class UserDto {
    @Null
    private Long id;

    @JsonProperty()
    @NotBlank(message = "Имя пользователя не может быть пустым")
    private String name;

    @Email
    private String email;

    @NotNull(message = "Возраст обязателен")
    @Min(value = 18, message = "Возраст может быть не меньше 18")
    @Max(value = 65, message = "Возраст может быть больше или равен 65")
    private Integer age;

    @Valid
    private List<PetDto> pets = new ArrayList<>();

    public UserDto() {
    }

    public UserDto(Long id, String name, String email, Integer age, List<PetDto> pets) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.age = age;
        this.pets = pets;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public List<PetDto> getPets() {
        return pets;
    }

    public void setPets(List<PetDto> pets) {
        this.pets = pets;
    }

    @Override
    public String toString() {
        return "UserDto{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", age=" + age +
                ", pets=" + pets +
                '}';
    }
}
