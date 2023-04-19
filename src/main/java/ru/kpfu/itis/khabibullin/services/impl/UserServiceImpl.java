package ru.kpfu.itis.khabibullin.services.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.kpfu.itis.khabibullin.dto.UserDto;
import ru.kpfu.itis.khabibullin.repositories.UsersRepository;
import ru.kpfu.itis.khabibullin.services.UserService;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UsersRepository usersRepository;

    @Override
    public UserDto getUserById(Long id) {
        return UserDto.from(usersRepository.findUserById(id));
    }

    @Override
    public UserDto getUserByUsername(String username) {
        return UserDto.from(usersRepository.findUserByUsername(username));
    }

    @Override
    public UserDto getUserByEmail(String email) {
        return  UserDto.from(usersRepository.findUserByEmail(email));
    }

    @Override
    public List<UserDto> getAllUsers() {
        return UserDto.from(usersRepository.findAll());
    }

    @Override
    public void saveUser(UserDto user) {
        usersRepository.save(UserDto.to(user));
    }

    @Override
    public void deleteUser(long id) {
        usersRepository.delete(id);
    }
}
