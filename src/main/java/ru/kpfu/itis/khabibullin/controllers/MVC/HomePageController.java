package ru.kpfu.itis.khabibullin.controllers.MVC;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
/**
 * @author Khabibullin Alisher
 */
@Controller
public class HomePageController {

    @GetMapping({"/", "/homepage"})
    public String home(Model model) {
        return "homepage";
    }

}
