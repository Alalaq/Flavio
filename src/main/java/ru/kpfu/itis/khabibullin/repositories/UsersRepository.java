package ru.kpfu.itis.khabibullin.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.kpfu.itis.khabibullin.models.User;

import java.util.Optional;

public interface UsersRepository extends JpaRepository<User, Long> {
    Optional<User> findUserById(long id);
    Optional<User> findUserByUsername(String username);
    Optional<User> findUserByEmail(String email);
}
