package com.junioroffers.domain.loginandregister;

import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class InMemoryUserRepositoryTestImpl implements UserRepository{
    private final Map<String, User> usersDatabase = new ConcurrentHashMap<>();
    @Override
    public User save(User entity) {
        User userToSave = new User(
                UUID.randomUUID().toString(),
                entity.username(),
                entity.password()
        );
        this.usersDatabase.put(userToSave.username(), userToSave);
        return userToSave;
    }

    @Override
    public Optional<User> findByUsername(String username) {
        return Optional.ofNullable(this.usersDatabase.get(username));
    }
}
