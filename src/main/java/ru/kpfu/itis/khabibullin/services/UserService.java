package ru.kpfu.itis.khabibullin.services;

import ru.kpfu.itis.khabibullin.dto.SignUpDto;
import ru.kpfu.itis.khabibullin.dto.UpdatedUserDto;
import ru.kpfu.itis.khabibullin.dto.UserDto;

import java.util.List;

public interface UserService {
    UserDto getUserById(Long id);
    UpdatedUserDto getUserForUpdateByEmail(String email);

    UpdatedUserDto getUserForUpdateById(Long id);

    UserDto getUserByUsername(String username);
    UserDto getUserByEmail(String email);
    List<UserDto> getAllUsers();
    void saveUser(SignUpDto user);
    void deleteUserById(long id);

    void updateUser(UpdatedUserDto user);

    List<UserDto> getAllUsersWithRole(String user);

    UpdatedUserDto getUserForUpdateByVerificationToken(String verificationToken);
}
