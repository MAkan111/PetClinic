package ru.makan1.petclinic.service;

import org.springframework.stereotype.Service;
import ru.makan1.petclinic.repository.InMemoryPetRepository;
import ru.makan1.petclinic.repository.InMemoryUserRepository;
import ru.makan1.petclinic.model.PetDto;

import java.util.List;
import java.util.Optional;

@Service
public class PetService {

    private final InMemoryPetRepository petRepository;
    private final InMemoryUserRepository userRepository;

    public PetService(InMemoryPetRepository petRepository,
                      InMemoryUserRepository userRepository) {
        this.petRepository = petRepository;
        this.userRepository = userRepository;
    }

    public PetDto createPet(PetDto petDto) {
        if (userRepository.findById(petDto.getUserId()).isEmpty()) {
            throw new IllegalArgumentException("User not found");
        }
        petRepository.save(petDto);
        return petDto;
    }

    public Optional<PetDto> getPetById(Long id) {
        return petRepository.findById(id);
    }

    public List<PetDto> getAllPets() {
        return petRepository.findAll();
    }

    public void updatePet(Long id, PetDto petDto) {
        petRepository.updateById(id, petDto);
    }

    public void deletePet(Long id) {
        petRepository.deleteById(id);
    }
}
