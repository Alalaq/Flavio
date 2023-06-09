package ru.kpfu.itis.khabibullin.controllers.MVC;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.kpfu.itis.khabibullin.dto.UpdatedUserDto;
import ru.kpfu.itis.khabibullin.exceptions.NotFoundException;
import ru.kpfu.itis.khabibullin.services.UserService;
import ru.kpfu.itis.khabibullin.utils.enums.State;

import static ru.kpfu.itis.khabibullin.aspects.ExceptionHandler.handleException;

/**
 * @author Khabibullin Alisher
 */
@Controller
public class EmailVerificationController {
    @Autowired
    private UserService userService;

    @GetMapping("/verify-email")
    public String verifyEmail(@RequestParam("token") String verificationToken, Model model) {
        try {
            UpdatedUserDto user = userService.getUserForUpdateByVerificationToken(verificationToken);
            user.setState(State.CONFIRMED);
            userService.updateUser(user);

            return "redirect:/login";
        } catch (NotFoundException e){
            handleException(e);
            return "redirect:/error";
        }

    }
}

