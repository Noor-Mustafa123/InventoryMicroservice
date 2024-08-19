package com.orderService.microservice.Controllers;


import lombok.*;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class ProductResponse {


    private String id;
    private String name;
    private String description;
    private String priceId;
    private String errorString;


}
