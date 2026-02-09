package ru.makan1.petclinic.Repository;

import ru.makan1.petclinic.model.UserDto;

import java.util.List;
import java.util.Optional;

public interface UserRepository {
    void save(UserDto userDto);

    Optional<UserDto> findById(Long id);

    List<UserDto> findAll();

    void deleteById(Long id);

    void updateById(UserDto userDto);
}
