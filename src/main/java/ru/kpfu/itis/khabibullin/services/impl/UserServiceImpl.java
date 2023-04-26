package ru.kpfu.itis.khabibullin.services.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.kpfu.itis.khabibullin.dto.SignUpDto;
import ru.kpfu.itis.khabibullin.dto.UpdatedUserDto;
import ru.kpfu.itis.khabibullin.dto.UserDto;
import ru.kpfu.itis.khabibullin.exceptions.NotFoundException;
import ru.kpfu.itis.khabibullin.models.User;
import ru.kpfu.itis.khabibullin.repositories.UsersRepository;
import ru.kpfu.itis.khabibullin.security.details.UserDetailsImpl;
import ru.kpfu.itis.khabibullin.services.UserService;
import ru.kpfu.itis.khabibullin.utils.Role;
import ru.kpfu.itis.khabibullin.utils.State;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UsersRepository usersRepository;

    private final PasswordEncoder passwordEncoder;

    @Override
    public UserDto getUserById(Long id) {
        return UserDto.from(usersRepository.findUserById(id) .orElseThrow(() -> new NotFoundException("User with id: <" + id + "> not found")));
    }

    @Override
    public UpdatedUserDto getUserForUpdateByEmail(String email) {
        User user = usersRepository.findUserByEmail(email).orElseThrow(() -> new NotFoundException("User with email: <" + email + "> not found"));
        return UpdatedUserDto.builder()
                .id(user.getId())
                .email(user.getEmail())
                .username(user.getUsername())
                .hashPassword(user.getHashPassword())
                .birthday(user.getBirthday())
                .phoneNumber(user.getPhoneNumber())
                .role(user.getRole())
                .state(user.getState())
                .orders(user.getOrders() == null ? new ArrayList<>() : user.getOrders())
                .build();
    }

    @Override
    public UserDto getUserByUsername(String username) {
        return UserDto.from(usersRepository.findUserByUsername(username).orElseThrow(() -> new NotFoundException("User with username: <" + username + "> not found")));
    }

    @Override
    public UserDto getUserByEmail(String email) {
        return  UserDto.from(usersRepository.findUserByEmail(email).orElseThrow(() -> new NotFoundException("User with email: <" + email + "> not found")));
    }

    @Override
    public List<UserDto> getAllUsers() {
        return UserDto.from(usersRepository.findAll());
    }

    @Override
    public void saveUser(SignUpDto user) {
        User userForSave = User.builder()
                .email(user.getEmail())
                .username(user.getUsername())
                .hashPassword(passwordEncoder.encode(user.getPassword()))
                .role(Role.USER)
                .state(State.CONFIRMED)
                .build();

        usersRepository.save(userForSave);
    }

    @Override
    public void deleteUser(long id) {
        usersRepository.deleteById(id);
    }

    @Override
    @CacheEvict(value = "users", key = "#user.id")
    public void updateUser(UpdatedUserDto user) {
        User userForSave = User.builder()
                .id(user.getId())
                .email(user.getEmail())
                .username(user.getUsername())
                .hashPassword(user.getHashPassword())
                .birthday(user.getBirthday())
                .phoneNumber(user.getPhoneNumber())
                .role(user.getRole())
                .state(user.getState())
                .orders(user.getOrders() == null ? new ArrayList<>() : user.getOrders())
                .build();

        updateAuthentication(userForSave);
        usersRepository.save(userForSave);
    }

    public static void updateAuthentication(User user) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null && auth.getPrincipal() instanceof UserDetailsImpl principal) {
            if (principal.getName().equals(user.getUsername())) {
                UserDetailsImpl updatedPrincipal = new UserDetailsImpl(
                        User.builder().
                        id(user.getId())
                                .email(user.getEmail())
                                .username(user.getUsername())
                                .hashPassword(user.getHashPassword())
                                .birthday(user.getBirthday())
                                .phoneNumber(user.getPhoneNumber())
                                .role(user.getRole())
                                .state(user.getState())
                                .orders(user.getOrders() == null ? new ArrayList<>() : user.getOrders())
                                .build()
                );
                Authentication newAuth = new UsernamePasswordAuthenticationToken(updatedPrincipal, auth.getCredentials(), updatedPrincipal.getAuthorities());
                SecurityContextHolder.getContext().setAuthentication(newAuth);
            }
        }
    }

}
