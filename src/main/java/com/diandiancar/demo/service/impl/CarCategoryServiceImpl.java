package com.diandiancar.demo.service.impl;

import com.diandiancar.demo.entity.CarCategory;
import com.diandiancar.demo.repository.CarCategoryRepository;
import com.diandiancar.demo.service.CarCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 *汽车类型
 */
@Service
public class CarCategoryServiceImpl implements CarCategoryService {

    @Autowired
    private CarCategoryRepository repository;

    @Override
    public CarCategory findOne(Integer categoryId) {
        return repository.findById(categoryId).get();
    }

    @Override
    public CarCategory save(CarCategory carCategory) {
        return repository.save(carCategory);
    }

    @Override
    public Page<CarCategory> findAll(Pageable pageable) {
        return repository.findAll(pageable);
    }

    @Override
    public List<CarCategory> findAll() {
        return repository.findAll();
    }

    @Override
    public Page<CarCategory> findByCategoryNameLike(String categoryName, Pageable pageable) {
        return repository.findByCategoryNameLike("%"+categoryName+"%",pageable);
    }

    @Override
    public List<CarCategory> findByCarCategoryTypeIn(List<Integer> categoryTypeList) {
        return repository.findByCategoryTypeIn(categoryTypeList);
    }


//    @Override
//    public List<CarCategory> findByCarCategoryTypeIn(List<Integer> categoryTypeList) {
//        return repository.findByCategoryTypeIn(categoryTypeList);
//    }
}
