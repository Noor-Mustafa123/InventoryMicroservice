package com.orderService.microservice.Services;


import com.orderService.microservice.DTO.ProductIdDTO;
import com.orderService.microservice.Models.ItemInfo;
import com.orderService.microservice.DTO.ProductQuantity;
import com.orderService.microservice.Models.ItemInfoWithPriceId;
import com.orderService.microservice.Repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StripeService {

    @Autowired
    public ProductRepository productRepo;


    public ProductQuantity productInventoryChecker(ProductIdDTO productIdDTO) {

        String productId = productIdDTO.getProductId();

        List<ItemInfoWithPriceId> listOfItems = productRepo.findItemInfoWithPriceIdByProductId(productId);

        if (listOfItems.size() == 1) {
          ItemInfoWithPriceId itemInfo1 =  listOfItems.get(0);

         return ProductQuantity.builder()
                  .productName(itemInfo1.getProductName())
                  .productQuantity(itemInfo1.getProductQuantity())
                  .build();


        } else if (listOfItems.size() <= 2) {

            return ProductQuantity.builder()
                    .productName(productIdDTO.getProductName())
                    .error("there are duplicate items present so broken inventory remove old items and add new stock")
                    .build();

        }
        else {
            return ProductQuantity.builder()
                    .productName(productIdDTO.getProductName())
                    .error("Item with ths name " +productIdDTO.getProductName()+ " is not present in the inventory")
                    .build();
        }

    }
}




