package ru.makan1.petclinic.controller;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.makan1.petclinic.model.PetDto;
import ru.makan1.petclinic.service.PetService;

import java.util.List;

@RestController
@RequestMapping("/api/v1/pets")
public class PetController {

    private final PetService petService;

    public PetController(PetService petService) {
        this.petService = petService;
    }

    @PostMapping("/create")
    public ResponseEntity<PetDto> create(@Valid @RequestBody PetDto petDto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(petService.createPet(petDto));
    }

    @GetMapping("/{id}")
    public ResponseEntity<PetDto> findById(@PathVariable Long id) {
        return petService.getPetById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping
    public List<PetDto> findAll() {
        return petService.getAllPets();
    }

    @PutMapping("/{id}")
    public void update(@PathVariable Long id,
                       @Valid @RequestBody PetDto petDto) {
        petService.updatePet(id, petDto);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        petService.deletePet(id);
    }
}
