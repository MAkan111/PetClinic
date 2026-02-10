package ru.makan1.petclinic.repository;

import org.springframework.stereotype.Component;
import ru.makan1.petclinic.exception.EntityNotFoundException;
import ru.makan1.petclinic.model.PetDto;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

@Component
public class InMemoryPetRepository implements PetRepository {

    private final Map<Long, PetDto> storage = new ConcurrentHashMap<>();
    private final AtomicLong counter = new AtomicLong(1);

    @Override
    public void save(PetDto pet) {
        if (pet.getId() == null) {
            pet.setId(counter.getAndIncrement());
        }
        storage.put(pet.getId(), pet);
    }

    @Override
    public Optional<PetDto> findById(Long id) {
        return Optional.ofNullable(storage.get(id));
    }

    @Override
    public List<PetDto> findByUserId(Long userId) {
        return storage.values().stream()
                .filter(p -> p.getUserId().equals(userId))
                .toList();
    }

    @Override
    public void updateById(Long id, PetDto petDto) {
        if (petDto == null || petDto.getId() == null) {
            throw new IllegalArgumentException("Сущность или ее ID не могут быть null");
        }
        if (!storage.containsKey(petDto.getId())) {
            throw new EntityNotFoundException("Питомец c " + petDto.getId() + " не найден");
        }
        storage.put(petDto.getId(), petDto);
    }

    @Override
    public void deleteById(Long id) {
        storage.remove(id);
    }

    public List<PetDto> findAll() {
        return new ArrayList<>(storage.values());
    }
}
