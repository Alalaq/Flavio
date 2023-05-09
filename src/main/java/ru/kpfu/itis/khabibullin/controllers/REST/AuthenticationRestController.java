package ru.kpfu.itis.khabibullin.controllers.REST;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
/**
 * @author Khabibullin Alisher
 */
@RestController
public class AuthenticationRestController {

    @GetMapping("/auth/check-authentication")
    public ResponseEntity<?> isAuthenticated(Principal principal){
        if (principal != null){
            return ResponseEntity.ok(HttpStatus.OK);
        } else {
            return ResponseEntity.badRequest().body(HttpStatus.UNAUTHORIZED);
        }
    }
}
