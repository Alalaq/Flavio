package ru.kpfu.itis.khabibullin.dto;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.kpfu.itis.khabibullin.models.Order;
import ru.kpfu.itis.khabibullin.utils.enums.Role;
import ru.kpfu.itis.khabibullin.utils.enums.State;

import java.time.LocalDate;
import java.util.List;
/**
 * @author Khabibullin Alisher
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UpdatedUserDto {

    @NotNull
    private Long id;

    @NotBlank
    private String username;

    @Email
    @NotBlank
    private String email;

    @NotBlank
    private String hashPassword;

    @NotBlank
    private String phoneNumber;

    @Past
    @NotNull
    private LocalDate birthday;

    @NotNull
    private Role role;

    @NotNull
    private State state;
    private List<Order> orders;
}
