package com.orderService.microservice.DTO;



import lombok.*;
import org.springframework.stereotype.Component;

@Component
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class ProductQuantity {


    public int productQuantity;

    public String productName;

    public String error;

}
