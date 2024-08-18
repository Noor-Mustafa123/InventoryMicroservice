package com.orderService.microservice.Repository;


import com.orderService.microservice.Models.ItemInfo;
import com.orderService.microservice.Models.ItemInfoWithPriceId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<ItemInfoWithPriceId, Integer> {

     List<ItemInfoWithPriceId> findItemInfoWithPriceIdByProductId(String productId);

}
