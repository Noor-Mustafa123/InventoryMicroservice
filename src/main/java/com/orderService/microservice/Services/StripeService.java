package com.orderService.microservice.Services;


import com.orderService.microservice.DTO.ProductIdDTO;
import com.orderService.microservice.Models.ItemInfo;
import com.orderService.microservice.DTO.ProductQuantity;
import com.orderService.microservice.Models.ItemInfoWithPriceId;
import com.orderService.microservice.Repository.ProductRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StripeService {

    @Autowired
    public ProductRepository productRepo;

@Transactional
    public ProductQuantity productInventoryChecker(ProductIdDTO productIdDTO) {

        String productId = productIdDTO.getProductId();

        List<ItemInfoWithPriceId> listOfItems = productRepo.findItemInfoWithPriceIdByProductId(productId);

        if (listOfItems.size() == 1) {
          ItemInfoWithPriceId itemInfo1 =  listOfItems.get(0);



        int currentProductQuantity = itemInfo1.getProductQuantity();


//        explicit type casting done here to convert parent type ot smaller type
        int minusProductQuantity = (int)productIdDTO.getProductQuantity();

        int finalProductQuantity =   currentProductQuantity - minusProductQuantity;

//            update the new value in the table
            productRepo.updateItemInfoWithPriceIdByProductId(productIdDTO.getProductId(),finalProductQuantity);


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


    @Transactional
    public boolean ifDuplicateAddToTotal(ItemInfo itemInfo) {
        List<ItemInfoWithPriceId> listOfItems = productRepo.findItemInfoWithPriceIdByProductName(itemInfo.getProductName());



        if(!listOfItems.isEmpty()){
        ItemInfoWithPriceId itemInfo1 = listOfItems.get(0);


        int currentProductQuantity = itemInfo1.getProductQuantity();


//        explicit type casting done here to convert parent type ot smaller type
        int minusProductQuantity = itemInfo.getProductQuantity();

        int finalProductQuantity = currentProductQuantity + minusProductQuantity;

//            update the new value in the table
        productRepo.updateItemInfoWithPriceIdByProductName(itemInfo1.getProductId(), finalProductQuantity);


        return true;
    }

        else{
            return false;
        }

        }


}




