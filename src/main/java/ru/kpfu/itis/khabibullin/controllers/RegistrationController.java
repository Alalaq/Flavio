package ru.kpfu.itis.khabibullin.controllers;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import ru.kpfu.itis.khabibullin.dto.SignUpDto;
import ru.kpfu.itis.khabibullin.exceptions.NotFoundException;
import ru.kpfu.itis.khabibullin.services.UserService;

@Controller
public class RegistrationController {
    @Autowired
    private UserService userService;

    @GetMapping("/registration")
    public String showRegistrationForm(Model model) {
        model.addAttribute("signUpDto", new SignUpDto());
        return "registration";
    }

    @PostMapping("/registration")
    public String processRegistrationForm(
            @Valid @ModelAttribute("signUpDto") SignUpDto signUpDto,
            BindingResult bindingResult,
            RedirectAttributes redirectAttributes
    ) {

        if (bindingResult.hasErrors()) {
            redirectAttributes.addAttribute(bindingResult.getAllErrors());
            return "redirect:/error";
        }

        try {
            userService.getUserByUsername(signUpDto.getUsername());
            userService.getUserByEmail(signUpDto.getEmail());
        } catch (NotFoundException ex){
            userService.saveUser(signUpDto);
          //  redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.signUpDto", bindingResult);
           // redirectAttributes.addFlashAttribute("signUpDto", signUpDto);
            return "redirect:/login";
        }

        bindingResult.rejectValue("username", "error.username", "Username already taken");
        return "registration";
    }
}
