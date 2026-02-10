package ru.makan1.petclinic.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Null;

public class PetDto {
    @Null
    private Long id;

    @NotBlank(message = "Имя питомца не может быть пустым")
    private String name;

    private Long userId;

    public PetDto() {
    }

    public PetDto(Long id, String name, Long userId) {
        this.id = id;
        this.name = name;
        this.userId = userId;
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

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    @Override
    public String toString() {
        return "PetDto{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", userId=" + userId +
                '}';
    }
}
