package ru.kpfu.itis.khabibullin.controllers.MVC;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class LoginController {
    @GetMapping({"/login"})
    public String login(@RequestParam(required = false) String error, Model model) {
        //TODO: handle wrong username/password and non confirmed user separately
        if (error != null) {
            model.addAttribute("errorMessage", "Invalid username or password");
        }
        return "login";
    }
}
