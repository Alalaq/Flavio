package ru.kpfu.itis.khabibullin.controllers.REST;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.kpfu.itis.khabibullin.dto.SignUpDto;
import ru.kpfu.itis.khabibullin.dto.UserDto;
import ru.kpfu.itis.khabibullin.services.UserService;

import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    // Get all users
    @GetMapping("/")
    public List<UserDto> getAllUsers() {
        return (userService.getAllUsers());
    }

    // Get a user by id
    @GetMapping("/{id}")
    public UserDto getUserById(@PathVariable Long id) {
        return (Objects.requireNonNull(userService.getUserById(id)));
    }

    // Create a new user
    @PostMapping("/")
    public void createUser(@RequestBody SignUpDto user) {
        userService.saveUser(user);
    }

    // Update an existing user
//    @PutMapping("/{id}")
//    public User updateUser(@PathVariable Long id, @RequestBody User user) {
//        User existingUser = usersRepository.findById(id).orElse(null);
//        if (existingUser != null) {
//            existingUser.setName(user.getName());
//            existingUser.setEmail(user.getEmail());
//            return usersRepository.save(existingUser);
//        }
//        return null;
//    }

    // Delete a user by id
    @DeleteMapping("/{id}")
    public void deleteUser(@PathVariable Long id) {
        userService.deleteUserById(id);
    }
}
