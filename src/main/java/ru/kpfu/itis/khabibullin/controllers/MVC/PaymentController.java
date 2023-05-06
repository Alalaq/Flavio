package ru.kpfu.itis.khabibullin.controllers.MVC;

import com.stripe.model.Charge;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.HashMap;
import java.util.Map;

@Controller
public class PaymentController {

    @PostMapping("/process-payment")
    public String processPayment(HttpServletRequest request) throws Exception {
        String token = request.getParameter("stripeToken");
        int amount = Integer.parseInt(request.getParameter("amount"));

        Map<String, Object> chargeParams = new HashMap<>();
        chargeParams.put("amount", amount);
        chargeParams.put("currency", "usd");
        chargeParams.put("source", token);

        Charge charge = Charge.create(chargeParams);

        // handle the payment response here
        return "payment-success";
    }

}
