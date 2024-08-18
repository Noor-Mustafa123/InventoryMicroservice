package com.orderService.microservice.DTO;

import lombok.*;
import org.springframework.stereotype.Component;

@Component
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class ProductIdDTO {

    public String productId;

    public String productName;

}
