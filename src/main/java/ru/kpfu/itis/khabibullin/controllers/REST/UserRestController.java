package ru.kpfu.itis.khabibullin.controllers.REST;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.kpfu.itis.khabibullin.dto.SignUpDto;
import ru.kpfu.itis.khabibullin.dto.UpdatedUserDto;
import ru.kpfu.itis.khabibullin.dto.UserDto;
import ru.kpfu.itis.khabibullin.services.UserService;
import ru.kpfu.itis.khabibullin.utils.enums.Role;
import ru.kpfu.itis.khabibullin.utils.enums.State;

import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserRestController {

    private final UserService userService;

    // Get all users
    @GetMapping("/")
    public List<UserDto> getAllUsers() {
        return (userService.getAllUsersWithRole("USER"));
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

    @PutMapping("/{id}")
    public void updateUser(@PathVariable Long id, @RequestParam String updateField, @RequestBody String updateValue) {
        UpdatedUserDto existingUser = userService.getUserForUpdateById(id);
        if (existingUser != null) {
            if (updateField.equalsIgnoreCase("state")) {
                existingUser.setState(State.valueOf(updateValue));
            } else if (updateField.equalsIgnoreCase("role")) {
                existingUser.setRole(Role.valueOf(updateValue.toUpperCase()));
            }
            userService.updateUser(existingUser);
        }
    }


    // Delete a user by id
    @DeleteMapping("/{id}")
    public void deleteUser(@PathVariable Long id) {
        userService.deleteUserById(id);
    }
}
