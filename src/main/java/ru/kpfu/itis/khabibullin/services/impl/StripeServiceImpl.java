package ru.kpfu.itis.khabibullin.services.impl;

import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.Charge;
import com.stripe.model.Customer;
import com.stripe.model.Price;
import com.stripe.model.Product;
import com.stripe.param.PriceCreateParams;
import com.stripe.param.PriceListParams;
import com.stripe.param.ProductCreateParams;
import com.stripe.param.ProductUpdateParams;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import ru.kpfu.itis.khabibullin.dto.DishDto;
import ru.kpfu.itis.khabibullin.services.StripeService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
/**
 * @author Khabibullin Alisher
 */
@Service
public class StripeServiceImpl implements StripeService {
    @Value("${stripe.api.key}")
    private String apiKey;

    @Override
    public String createCustomer(String email, String token) {
        String id = null;
        try {
            Stripe.apiKey = apiKey;
            Map<String, Object> customerParams = new HashMap<>();
            // add customer unique id here to track them in your web application
            customerParams.put("description", "Customer for " + email);
            customerParams.put("email", email);

            customerParams.put("source", token); // ^ obtained with Stripe.js
            //create a new customer
            Customer customer = Customer.create(customerParams);
            id = customer.getId();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return id;
    }

    @Override
    public void createProductsAndPrices(List<DishDto> dishes) throws StripeException {
        Stripe.apiKey = apiKey;

        for (DishDto dish : dishes) {
            Long priceAmount = Long.valueOf(String.valueOf(dish.getPrice()));
            String currency = "rub";
            Price existingPrice = null;

            // Retrieve a list of prices with the same amount and currency
            List<Price> prices = Price.list(
                    new PriceListParams.Builder()
                            .putExtraParam("lookup_keys", "price_" + priceAmount + "_" + currency)
                            .build()
            ).getData();

            // Check whether there is an existing price with the same amount and currency
            for (Price price : prices) {
                if (price.getUnitAmount().equals(priceAmount) && price.getCurrency().equals(currency)) {
                    existingPrice = price;
                    break;
                }
            }

            // If an existing price was not found, create a new one
            if (existingPrice == null) {
                ProductCreateParams productParams = ProductCreateParams.builder()
                        .setName(dish.getName())
                        .setDescription(dish.getDescription())
                        .setActive(true)
                        .build();

                Product product = Product.create(productParams);

                PriceCreateParams priceParams = PriceCreateParams.builder()
                        .setProduct(product.getId())
                        .setCurrency(currency)
                        .setUnitAmount(priceAmount)
                        .build();

                Price price = Price.create(priceParams);

                Map<String, String> metadata = new HashMap<>();
                metadata.put("price_id", price.getId());

                ProductUpdateParams updateParams = ProductUpdateParams.builder()
                        .setMetadata(metadata)
                        .build();

                product.update(updateParams);
            }
            // If an existing price was found, update the product with the existing price ID
            else {
                ProductCreateParams productParams = ProductCreateParams.builder()
                        .setName(dish.getName())
                        .setDescription(dish.getDescription())
                        .setActive(true)
                        .putMetadata("price_id", existingPrice.getId())
                        .build();

                Product.create(productParams);
            }
        }
    }

    @Override
    public String createCharge(String email, String token, int amount) {
        String id = null;
        try {
            Stripe.apiKey = apiKey;
            Map<String, Object> chargeParams = new HashMap<>();
            chargeParams.put("amount", amount);
            chargeParams.put("currency", "usd");
            chargeParams.put("description", "Charge for " + email);
            chargeParams.put("source", token); // ^ obtained with Stripe.js

            //create a charge
            Charge charge = Charge.create(chargeParams);
            id = charge.getId();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return id;
    }
}
