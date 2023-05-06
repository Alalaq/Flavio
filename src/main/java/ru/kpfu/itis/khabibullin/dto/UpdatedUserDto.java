package ru.kpfu.itis.khabibullin.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.kpfu.itis.khabibullin.models.Order;
import ru.kpfu.itis.khabibullin.utils.enums.Role;
import ru.kpfu.itis.khabibullin.utils.enums.State;

import java.time.LocalDate;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UpdatedUserDto {
    private Long id;
    private String username;
    private String email;
    private String hashPassword;
    private String phoneNumber;
    private LocalDate birthday;
    private Role role;
    private State state;
    private List<Order> orders;
}
