package com.orderService.microservice.Models;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.*;
import org.springframework.stereotype.Component;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Component
public class ItemInfo {
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        public Integer id;
        private String productName;
        private String productDesc;
        private long productPrice;


}
