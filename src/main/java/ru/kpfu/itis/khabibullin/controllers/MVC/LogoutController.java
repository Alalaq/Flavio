package ru.kpfu.itis.khabibullin.controllers.MVC;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
/**
 * @author Khabibullin Alisher
 */
@Controller
public class LogoutController {
    @GetMapping("/logout")
    public String logoutPage() {
        return "logout";
    }

    @PostMapping("/logout")
    public String logout(HttpServletRequest request) throws ServletException {
        request.logout();
        return "redirect:/login?logout";
    }

}
