package ru.kpfu.itis.khabibullin.controllers;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class LogoutController {
    @GetMapping("/logout")
    public String logoutPage() {
        return "logout"; // return the view for your custom logout page
    }

    @PostMapping("/logout")
    public String logout(HttpServletRequest request) throws ServletException {
        request.logout(); // logout the user
        return "redirect:/login?logout"; // redirect to the login page with a logout parameter
    }

}
