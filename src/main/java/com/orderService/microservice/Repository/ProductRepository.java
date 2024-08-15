package com.orderService.microservice.Repository;


import com.orderService.microservice.Models.ItemInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<ItemInfo, Integer> {



}
