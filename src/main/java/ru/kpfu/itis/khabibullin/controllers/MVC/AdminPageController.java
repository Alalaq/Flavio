package ru.kpfu.itis.khabibullin.controllers.MVC;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
/**
 * @author Khabibullin Alisher
 */
@Controller
public class AdminPageController {
    @GetMapping("/admin-page")
    public String getAdminPage(){
        return "admin-page";
    }
}
