package com.orderService.microservice.Controllers;


import com.orderService.microservice.DTO.ProductIdDTO;
import com.orderService.microservice.Models.ItemInfo;
import com.orderService.microservice.DTO.ProductQuantity;
import com.orderService.microservice.Models.ItemInfoWithPriceId;
import com.orderService.microservice.Repository.ProductRepository;
import com.orderService.microservice.Services.StripeService;
import com.stripe.param.ProductCreateParams;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import com.stripe.exception.StripeException;
import com.stripe.model.*;
import com.stripe.param.PriceCreateParams;

@Controller


@RequestMapping("/UserData")
public class ProductController {

    @Autowired
    private ProductResponse productResponse;

    @Autowired
    public StripeConfig stripeConfig;

    private static final Logger logger = LoggerFactory.getLogger(Controller.class);

    @Autowired
    public ProductRepository productRepository;

    @Autowired
    public StripeService stripeService;


    @PostMapping("/AddProduct")
    public ResponseEntity<ProductResponse> addNewProduct(@RequestBody ItemInfo itemInfo) {


      boolean booleanValue =  stripeService.ifDuplicateAddToTotal(itemInfo);

            if(booleanValue){
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ProductResponse(null,null,null,null,"There is a duplicate product already in the database and the quantity of the product has been updated"));
            }

        try {

            stripeConfig.init();
// ProductCreateParams.builder() is creating a new instance of ProductCreateParams.Builder, which you can then use to set the properties of a new ProductCreateParams object and create it with the build method.
            ProductCreateParams Parameters = ProductCreateParams.builder()
                    .setName(itemInfo.getProductName())
                    .setDescription(itemInfo.getProductDesc())
                    .build();
            Product productObj = Product.create(Parameters);

            PriceCreateParams priceParams = PriceCreateParams.builder()
                    .setUnitAmount(itemInfo.getProductPrice())
                    .setCurrency("usd")
                    // use the ID of the product you just created
                    .setProduct(productObj.getId())
                    .build();

            Price price = Price.create(priceParams);


//Created this DTO to transfer data as a response this is an empty dto class which i am sending back as a response
            productResponse.setId(productObj.getId());
            productResponse.setName(productObj.getName());
            productResponse.setDescription(productObj.getDescription());
            productResponse.setPriceId(price.getId());


          ItemInfoWithPriceId itemInfoWithPriceIdObj =  ItemInfoWithPriceId.builder()
                    .productName(itemInfo.getProductName())
                    .productQuantity(itemInfo.getProductQuantity())
                    .productDesc(itemInfo.getProductDesc())
                    .productPrice(itemInfo.getProductPrice())
                    .productId(price.getId())
                    .build();


            productRepository.save(itemInfoWithPriceIdObj);

            return ResponseEntity.ok(productResponse);

        } catch (StripeException e) {
            logger.error("An error occurred: ", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }







    }




    @PostMapping("/Stock")
    public ResponseEntity<ProductQuantity> checkProductStock(@RequestBody ProductIdDTO productIdDTO) {

          ProductQuantity productResponse = stripeService.productInventoryChecker(productIdDTO);

          if(productResponse.getError() != null){
            return  ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(productResponse);
          }
          else{
              return ResponseEntity.status(HttpStatus.OK).body(productResponse);
          }


    }

}
