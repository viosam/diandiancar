package com.diandiancar.demo.repository;

import com.diandiancar.demo.entity.Shop;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ShopRepository extends JpaRepository<Shop, String> {

    List<Shop> findByCity(String city);
}
