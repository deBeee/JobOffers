package com.junioroffers.domain.loginandregister;

import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class InMemoryUserRepositoryTestImpl implements UserRepository{
    private final Map<String, User> usersDatabase = new ConcurrentHashMap<>();
    @Override
    public User save(User userToSave) {
        User user = new User(
                UUID.randomUUID().toString(),
                userToSave.username(),
                userToSave.password()
        );
        this.usersDatabase.put(user.username(), user);
        return user;
    }

    @Override
    public Optional<User> findByUsername(String username) {
        return Optional.ofNullable(this.usersDatabase.get(username));
    }
}
