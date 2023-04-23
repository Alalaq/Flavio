package ru.kpfu.itis.khabibullin.models;

import jakarta.persistence.*;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.kpfu.itis.khabibullin.utils.Role;
import ru.kpfu.itis.khabibullin.utils.State;

import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "account")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id", nullable = false, updatable = false)
    private Long id;

    @Column(name = "username", nullable = false, unique = true)
    @NotBlank(message = "Username is required")
    @Size(min = 4, max = 20, message = "Username length must be between 4 and 20 characters")
    private String username;

    @Column(name = "password", nullable = false)
    @NotBlank(message = "Password must be not blank")
    @Size(min = 6, max = 100, message = "Password length must be between 6 and 100 characters")
    private String hashPassword;

    @Column(name = "email", nullable = false, unique = true)
    @NotBlank(message = "Email is required")
    @Email(message = "Email must be a valid email address")
    private String email;

    @Enumerated(value = EnumType.STRING)
    @Column(name = "role", nullable = false)
    private Role role;

    @Enumerated(value = EnumType.STRING)
    @Column(name = "state_of_user", nullable = false)
    private State state;


    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Order> orders = new ArrayList<>();
}