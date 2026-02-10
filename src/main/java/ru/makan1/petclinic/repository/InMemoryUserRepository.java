package ru.makan1.petclinic.repository;

import org.springframework.stereotype.Component;
import ru.makan1.petclinic.exception.EntityNotFoundException;
import ru.makan1.petclinic.model.UserDto;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

@Component
public class InMemoryUserRepository implements UserRepository {

    private final Map<Long, UserDto> storage = new ConcurrentHashMap<>();
    private final AtomicLong counter = new AtomicLong(1);

    @Override
    public void save(UserDto user) {
        if (user.getId() == null) {
            user.setId(counter.getAndIncrement());
        }
        storage.put(user.getId(), user);
    }

    @Override
    public Optional<UserDto> findById(Long id) {
        return Optional.ofNullable(storage.get(id));
    }

    @Override
    public List<UserDto> findAll() {
        return new ArrayList<>(storage.values());
    }

    @Override
    public void deleteById(Long id) {
        storage.remove(id);
    }

    @Override
    public void updateById(Long id, UserDto userDto) {
        if (userDto == null || userDto.getId() == null) {
            throw new IllegalArgumentException("Сущность или ее ID не могут быть null");
        }
        if (!storage.containsKey(userDto.getId())) {
            throw new EntityNotFoundException("Пользователь c " + userDto.getId() + " не найден");
        }
        storage.put(userDto.getId(), userDto);
    }
}
