package ru.kpfu.itis.khabibullin.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MyProfileController {
    @GetMapping({"/profile", "/profile.html"})
    public String profile(Model model) {
        return "profile";
    }
}
