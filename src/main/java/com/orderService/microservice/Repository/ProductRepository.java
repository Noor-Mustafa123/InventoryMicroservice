package com.orderService.microservice.Repository;



import com.orderService.microservice.Models.ItemInfoWithPriceId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<ItemInfoWithPriceId, Integer> {

     List<ItemInfoWithPriceId> findItemInfoWithPriceIdByProductId(String productId);

//     When you use the @Query annotation in Spring Data JPA, the method name can be anything you like
@Modifying
@Query("UPDATE ItemInfoWithPriceId i SET i.productQuantity = :newQuantity WHERE i.productId = :productId")
    void updateItemInfoWithPriceIdByProductId(String productId, int newQuantity);


    List<ItemInfoWithPriceId> findItemInfoWithPriceIdByProductName(String productName);


    @Modifying
    @Query("UPDATE ItemInfoWithPriceId i SET i.productQuantity = :newQuantity WHERE i.productId = :productId")
    void updateItemInfoWithPriceIdByProductName(String productId, int newQuantity);



}
