package com.diandiancar.demo.repository;

import com.diandiancar.demo.entity.CreditLevel;
import org.springframework.data.jpa.repository.JpaRepository;


public interface CreditRepository extends JpaRepository<CreditLevel, Integer> {

    CreditLevel findByCreditLevel(Integer creditLevel);

}
