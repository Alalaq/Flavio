package ru.kpfu.itis.khabibullin.services;

import ru.kpfu.itis.khabibullin.dto.UserDto;

import java.util.List;

public interface UserService {
    UserDto getUserById(Long id);
    UserDto getUserByUsername(String username);
    UserDto getUserByEmail(String email);
    List<UserDto> getAllUsers();
    void saveUser(UserDto user);
    void deleteUser(long id);
}
