package com.diandiancar.demo.repository;

import com.diandiancar.demo.entity.CarCategory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CarCategoryRepository extends JpaRepository<CarCategory, Integer> {

    List<CarCategory> findByCategoryTypeIn(List<Integer> categoryTypeList);
    Page<CarCategory> findByCategoryNameLike(String categoryName, Pageable pageable);
}