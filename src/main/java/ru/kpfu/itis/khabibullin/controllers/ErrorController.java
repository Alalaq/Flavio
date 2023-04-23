package ru.kpfu.itis.khabibullin.controllers;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class ErrorController implements org.springframework.boot.web.servlet.error.ErrorController {

    @RequestMapping("/error")
    @ResponseBody
    String error(HttpServletRequest request, Model model) {
        model.addAttribute("message", "An error has occurred");
        model.addAttribute("status", request);
        model.addAttribute("status", request.getAttribute("javax.servlet.error.status_code"));
        request.setAttribute("message", "An error has occurred");
        request.setAttribute("status", request.getAttribute("javax.servlet.error.status_code"));
        return "errors/error";
    }

    @RequestMapping("/403")
    public String handle403Error(Model model) {
        return "errors/403error";
    }


}
