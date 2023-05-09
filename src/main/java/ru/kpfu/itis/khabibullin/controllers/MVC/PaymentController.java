package ru.kpfu.itis.khabibullin.controllers.MVC;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import ru.kpfu.itis.khabibullin.dto.OrderDto;
import ru.kpfu.itis.khabibullin.services.OrderService;
import ru.kpfu.itis.khabibullin.services.StripeService;
import ru.kpfu.itis.khabibullin.utils.enums.StateOfOrder;

import java.io.IOException;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.Objects;

@Controller
@RequiredArgsConstructor
public class PaymentController {
    private final OrderService orderService;
    private final StripeService stripeService;

    @Value("${stripe.public.key}")
    private String API_PUBLIC_KEY;

    @GetMapping("/process-payment")
    public String getPaymentPage(Model model, @CookieValue(value = "currentOrder", required = false) String encodedCurrentOrderCookie, @CookieValue(value = "hasOrder") Boolean hasOrder) {
        if (hasOrder) {
            OrderDto order = new OrderDto();
            if (!encodedCurrentOrderCookie.isEmpty()) {
                String decodedCurrentOrderCookie = URLDecoder.decode(encodedCurrentOrderCookie, StandardCharsets.UTF_8);
                try {
                    order = new OrderDto(decodedCurrentOrderCookie);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            model.addAttribute("stripePublicKey", API_PUBLIC_KEY);
            model.addAttribute("order", order);
            return "charge";
        } else {
            //TODO: handle somehow
            return "redirect:/homepage";
        }
    }


    @GetMapping("/payment-success")
    public String getPaymentSuccessPage(Model model, @CookieValue(value = "currentOrder", required = false) String currentOrderJson, @CookieValue(value = "hasOrder") Boolean hasOrder, HttpServletResponse response) {
        if (hasOrder) {
            OrderDto currentOrder = null;
            if (currentOrderJson != null) {
                try {
                    currentOrder = new ObjectMapper().readValue(currentOrderJson, OrderDto.class);
                    System.out.println(currentOrder);
                } catch (JsonProcessingException e) {
                    e.printStackTrace();
                }
            }
            model.addAttribute("order", currentOrder);

            Cookie currentOrderCookie = new Cookie("currentOrder", null);
            currentOrderCookie.setMaxAge(0);
            response.addCookie(currentOrderCookie);


            Cookie hasOrderCookie = new Cookie("hasOrder", null);
            hasOrderCookie.setMaxAge(0);
            response.addCookie(hasOrderCookie);

            return "payment-success";
        } else {
            //TODO: handle somehow
            return "redirect:/homepage";
        }
    }

    @PostMapping("/process-payment")
    public ResponseEntity<String> createCharge(String email, String token, @CookieValue(value = "currentOrder", required = false) String currentOrderJson) {
        OrderDto currentOrder = null;
        if (currentOrderJson != null) {
            try {
                currentOrder = new ObjectMapper().readValue(currentOrderJson, OrderDto.class);
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
        }

        if (token == null) {
            Objects.requireNonNull(currentOrder).setState(StateOfOrder.FAILED_PAYMENT);
            orderService.saveOrder(currentOrder);
            return ResponseEntity.badRequest().body("Stripe payment token is missing. Please, try again later.");
        }

        String chargeId = stripeService.createCharge(email, token, Objects.requireNonNull(currentOrder).getTotal());
        if (chargeId == null) {
            currentOrder.setState(StateOfOrder.FAILED_PAYMENT);
            orderService.saveOrder(currentOrder);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred while trying to create a charge.");
        }

        currentOrder.setState(StateOfOrder.CONFIRMED);
        orderService.saveOrder(currentOrder);


        return ResponseEntity.status(HttpStatus.OK).body("Successful payment!");
    }

}
