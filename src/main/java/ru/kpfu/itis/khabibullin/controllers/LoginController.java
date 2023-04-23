package ru.kpfu.itis.khabibullin.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LoginController {
    @GetMapping({"/login"})
    public String login(Model model) {

        //TODO: handle errors
//        if (bindingResult.hasErrors()) {
//            redirectAttributes.addAttribute(bindingResult.getAllErrors());
//            return "redirect:/error";
//        }

        return "login";
    }

}
