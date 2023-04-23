package ru.kpfu.itis.khabibullin.services.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.kpfu.itis.khabibullin.dto.SignUpDto;
import ru.kpfu.itis.khabibullin.dto.UserDto;
import ru.kpfu.itis.khabibullin.exceptions.NotFoundException;
import ru.kpfu.itis.khabibullin.models.User;
import ru.kpfu.itis.khabibullin.repositories.UsersRepository;
import ru.kpfu.itis.khabibullin.services.UserService;
import ru.kpfu.itis.khabibullin.utils.Role;
import ru.kpfu.itis.khabibullin.utils.State;

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
}
