package ru.makan1.petclinic.service;

import jakarta.validation.Valid;
import org.springframework.stereotype.Service;
import ru.makan1.petclinic.repository.PetRepository;
import ru.makan1.petclinic.repository.UserRepository;
import ru.makan1.petclinic.model.PetDto;
import ru.makan1.petclinic.model.UserDto;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final PetRepository petRepository;

    public UserService(UserRepository userRepository,
                       PetRepository petRepository
    ) {
        this.userRepository = userRepository;
        this.petRepository = petRepository;
    }

    public UserDto createUser(UserDto userDto) {
        userRepository.save(userDto);

        if (userDto.getPets() != null) {
            for (PetDto pet : userDto.getPets()) {
                pet.setUserId(userDto.getId());
                petRepository.save(pet);
            }
        }

        return enrich(userDto);
    }

    public Optional<UserDto> getUserById(Long id) {
        return userRepository.findById(id).map(this::enrich);
    }

    public List<UserDto> getAllUsers() {
        return userRepository.findAll().stream()
                .map(this::enrich)
                .toList();
    }

    public void updateUser(Long id, @Valid UserDto userDto) {
        UserDto userToUpdate = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Пользователь не найден"));
        userToUpdate.setName(userDto.getName());
        userToUpdate.setAge(userDto.getAge());
        userToUpdate.setEmail(userDto.getEmail());
        userToUpdate.setPets(userDto.getPets());
        userRepository.save(userToUpdate);
    }

    public void deleteUser(Long userId) {
        petRepository.findByUserId(userId)
                .forEach(p -> petRepository.deleteById(p.getId()));
        userRepository.deleteById(userId);
    }

    public void addPetToUser(Long userId, PetDto petDto) {
        if (userRepository.findById(userId).isEmpty()) {
            throw new IllegalArgumentException("Пользователь с userId: " + userId + " не найден");
        }
        petDto.setUserId(userId);
        petRepository.save(petDto);
    }

    private UserDto enrich(UserDto user) {
        user.setPets(petRepository.findByUserId(user.getId()));
        return user;
    }
}
