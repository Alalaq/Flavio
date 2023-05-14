package ru.kpfu.itis.khabibullin.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
/**
 * @author Khabibullin Alisher
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SignUpDto {
    @NotBlank
    @Email
    private String email;

    @NotBlank
    private String username;

    @NotNull
    private String password;

    @NotBlank
    private String verificationToken;
}
