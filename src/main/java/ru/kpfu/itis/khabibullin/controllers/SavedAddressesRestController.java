package ru.kpfu.itis.khabibullin.controllers;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.Data;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@Data
public class SavedAddressesRestController {
    @GetMapping("/saved-addresses")
    public List<String> getSavedAddresses(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        List<String> savedAddresses = new ArrayList<>();

        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("saved-address")) {
                    savedAddresses.add(cookie.getValue());
                }
            }
        }

        return savedAddresses;
    }

    @PostMapping("/add-address")
    public void addSavedAddress(@RequestBody String address, HttpServletResponse response) {
        Cookie cookie = new Cookie("saved-address", address);
        cookie.setMaxAge(365 * 24 * 60 * 60); // set cookie to expire in one year
        response.addCookie(cookie);
    }
}
