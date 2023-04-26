package ru.kpfu.itis.khabibullin.controllers.MVC;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.kpfu.itis.khabibullin.dto.UpdatedUserDto;
import ru.kpfu.itis.khabibullin.dto.UserDto;
import ru.kpfu.itis.khabibullin.services.UserService;

import java.security.Principal;
import java.util.ArrayList;

@Controller
@RequestMapping({"/myProfile", "/profile"})
@RequiredArgsConstructor
public class MyProfileController {
    private final UserService userService;

    private final PasswordEncoder passwordEncoder;

    @GetMapping
    public String profile(@RequestParam(required = false) String error,
                          Model model,
                          Principal principal) {
        if (error != null) {
            model.addAttribute("errorMessage", error);
        }
        UserDto userDto = userService.getUserByEmail(principal.getName());
        model.addAttribute("userDto", userDto);
        return "myProfile";
    }

    @PostMapping("/save-information")
    public String updateProfile(@RequestParam(value = "password", required = false) String password,
                                @ModelAttribute("updatedUserDto") @Valid UpdatedUserDto updatedUserDto,
                                BindingResult result,
                                Principal principal,
                                Model model) {
        if (result.hasErrors()) {
            model.addAttribute("errorMessage", "Wrong email format.");
            return "redirect:/profile?error=wrongEmailFormat";
        }
        UpdatedUserDto updatedUser = userService.getUserForUpdateByEmail(principal.getName());
        if (updatedUserDto.getUsername() != null && !updatedUserDto.getUsername().equals("")) {
            updatedUser.setUsername(updatedUserDto.getUsername());
        }
        if (updatedUserDto.getEmail() != null && !updatedUserDto.getEmail().equals("")) {
            updatedUser.setEmail(updatedUserDto.getEmail());
        }
        if (updatedUserDto.getBirthday() != null) {
            updatedUser.setBirthday(updatedUserDto.getBirthday());
        }
        if (updatedUserDto.getPhoneNumber() != null && !updatedUserDto.getPhoneNumber().equals("")) {
            updatedUser.setPhoneNumber(updatedUserDto.getPhoneNumber());
        }
        if (updatedUser.getOrders() != null) {
            updatedUser.getOrders().removeIf(order -> order.getUser() == null);
        } else {
            updatedUser.setOrders(new ArrayList<>());
        }
        if (!passwordEncoder.matches(password, updatedUser.getHashPassword())) {
            result.rejectValue("hashPassword", "passwordMismatch", "Wrong password");
            model.addAttribute("errorMessage", "Wrong password");
            return "redirect:/profile?error=wrongPassword";
        } else {
            if (updatedUserDto.getHashPassword() != null && !updatedUserDto.getHashPassword().equals("")) {
                updatedUser.setHashPassword(passwordEncoder.encode(updatedUserDto.getHashPassword()));
            }
            userService.updateUser(updatedUser);
            return "redirect:/profile";
        }

    }

    @GetMapping("/save-information?continue")
    public String returnToProfile(){
        return "redirect:/profile";
    }
}
