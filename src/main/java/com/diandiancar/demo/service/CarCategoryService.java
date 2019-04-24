package com.diandiancar.demo.service;

import com.diandiancar.demo.entity.CarCategory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface CarCategoryService {

    CarCategory findOne(Integer categoryId);

    CarCategory save(CarCategory carCategory);

    //分页查找
    Page<CarCategory> findAll(Pageable pageable);

    //直接查找所有
    List<CarCategory> findAll();

    Page<CarCategory> findByCategoryNameLike(String categoryName, Pageable pageable);

    List<CarCategory> findByCarCategoryTypeIn(List<Integer> categoryTypeList);


}
