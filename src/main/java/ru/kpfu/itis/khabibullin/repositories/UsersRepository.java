package ru.kpfu.itis.khabibullin.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.kpfu.itis.khabibullin.models.User;
import ru.kpfu.itis.khabibullin.utils.enums.Role;

import java.util.List;
import java.util.Optional;
/**
 * @author Khabibullin Alisher
 */
public interface UsersRepository extends JpaRepository<User, Long> {
    Optional<User> findUserById(long id);
    Optional<User> findUserByUsername(String username);
    Optional<User> findUserByEmail(String email);
    List<User> findUsersByRoleOrderById(Role role);

    Optional<User> findUsersByVerificationToken(String verificationToken);
}
