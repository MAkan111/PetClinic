package ru.makan1.petclinic.Repository;

import org.springframework.stereotype.Component;
import ru.makan1.petclinic.model.UserDto;

import java.util.*;
import java.util.concurrent.atomic.AtomicLong;

@Component
public class InMemoryUserRepository {

    private final Map<Long, UserDto> storage = new HashMap<>();
    private final AtomicLong counter = new AtomicLong(1);

    public void save(UserDto user) {
        if (user.getId() == null) {
            user.setId(counter.getAndIncrement());
        }
        storage.put(user.getId(), user);
    }

    public Optional<UserDto> findById(Long id) {
        return Optional.ofNullable(storage.get(id));
    }

    public List<UserDto> findAll() {
        return new ArrayList<>(storage.values());
    }

    public void deleteById(Long id) {
        storage.remove(id);
    }
}
