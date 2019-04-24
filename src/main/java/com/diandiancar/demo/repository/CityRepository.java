package com.diandiancar.demo.repository;

import com.diandiancar.demo.entity.City;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CityRepository extends JpaRepository<City, String> {

    List<City> findByProvinceNum(Integer provinceNum);
}