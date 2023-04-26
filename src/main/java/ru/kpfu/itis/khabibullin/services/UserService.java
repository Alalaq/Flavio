package ru.kpfu.itis.khabibullin.services;

import ru.kpfu.itis.khabibullin.dto.SignUpDto;
import ru.kpfu.itis.khabibullin.dto.UpdatedUserDto;
import ru.kpfu.itis.khabibullin.dto.UserDto;

import java.util.List;

public interface UserService {
    UserDto getUserById(Long id);
    UpdatedUserDto getUserForUpdateByEmail(String email);
    UserDto getUserByUsername(String username);
    UserDto getUserByEmail(String email);
    List<UserDto> getAllUsers();
    void saveUser(SignUpDto user);
    void deleteUser(long id);

    void updateUser(UpdatedUserDto user);
}
