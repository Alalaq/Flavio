package ru.kpfu.itis.khabibullin.services;

import com.stripe.exception.StripeException;
import ru.kpfu.itis.khabibullin.dto.DishDto;

import java.util.List;
/**
 * @author Khabibullin Alisher
 */
public interface StripeService {
    String createCustomer(String email, String token);

    void createProductsAndPrices(List<DishDto> dishes) throws StripeException;

    String createCharge(String email, String token, int amount);
}
