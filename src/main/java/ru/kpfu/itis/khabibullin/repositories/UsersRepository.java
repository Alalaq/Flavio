package ru.kpfu.itis.khabibullin.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.kpfu.itis.khabibullin.models.User;

import java.util.List;

public interface UsersRepository extends JpaRepository<User, Long> {
    User findUserById(long id);
    User findUserByUsername(String username);
    User findUserByEmail(String email);
    List<User> findAll();
    void delete(long id);
}
