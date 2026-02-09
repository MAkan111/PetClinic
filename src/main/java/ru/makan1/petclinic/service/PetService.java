package ru.makan1.petclinic.service;

import org.springframework.stereotype.Service;
import ru.makan1.petclinic.Repository.InMemoryPetRepository;
import ru.makan1.petclinic.Repository.InMemoryUserRepository;
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

    public List<PetDto> getPetsByUserId(Long userId) {
        return petRepository.findByUserId(userId);
    }

    public void updatePet(Long id, PetDto petDto) {
        PetDto pet = petRepository.findById(id).orElseThrow();
        pet.setName(petDto.getName());
    }

    public void deletePet(Long id) {
        petRepository.deleteById(id);
    }
}
