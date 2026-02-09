package ru.makan1.petclinic.Repository;

import ru.makan1.petclinic.model.PetDto;

import java.util.List;
import java.util.Optional;

public interface PetRepository {
    void save(PetDto petDto);

    Optional<PetDto> findById(Long id);

    List<PetDto> findByUserId(Long userId);

    void deleteById(Long id);

    void updateById(PetDto petDto);
}
