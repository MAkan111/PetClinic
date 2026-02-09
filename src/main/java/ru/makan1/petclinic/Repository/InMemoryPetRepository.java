package ru.makan1.petclinic.Repository;

import org.springframework.stereotype.Component;
import ru.makan1.petclinic.model.PetDto;

import java.util.*;
import java.util.concurrent.atomic.AtomicLong;

@Component
public class InMemoryPetRepository {

    private final Map<Long, PetDto> storage = new HashMap<>();
    private final AtomicLong counter = new AtomicLong(1);

    public void save(PetDto pet) {
        if (pet.getId() == null) {
            pet.setId(counter.getAndIncrement());
        }
        storage.put(pet.getId(), pet);
    }

    public Optional<PetDto> findById(Long id) {
        return Optional.ofNullable(storage.get(id));
    }

    public List<PetDto> findByUserId(Long userId) {
        return storage.values().stream()
                .filter(p -> p.getUserId().equals(userId))
                .toList();
    }

    public void deleteById(Long id) {
        storage.remove(id);
    }

    public List<PetDto> findAll() {
        return new ArrayList<>(storage.values());
    }
}
