package com.junioroffers.domain.loginandregister;

import java.util.Optional;

public interface UserRepository {
    User save(User userToSave);

    Optional<User> findByUsername(String username);
}
