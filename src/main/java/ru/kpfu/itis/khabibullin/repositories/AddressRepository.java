package ru.kpfu.itis.khabibullin.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.kpfu.itis.khabibullin.models.Address;

import java.util.List;
/**
 * @author Khabibullin Alisher
 */
public interface AddressRepository extends JpaRepository<Address, Long> {
    List<Address> findByUserId(Long user_id);
}
