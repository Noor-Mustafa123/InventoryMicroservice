package com.orderService.microservice.Controllers;


import com.stripe.Stripe;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class StripeConfig {


    @Value("${stripe.api.key}")
    private String stripeApiKey;



    //    the configuration classes can have initialization method too
    @PostConstruct
    public void init() {
        Stripe.apiKey = stripeApiKey;
    }


}
