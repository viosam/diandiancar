package com.diandiancar.demo.service.impl;

import com.diandiancar.demo.entity.CarCategory;
import com.diandiancar.demo.service.impl.CarCategoryServiceImpl;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@SpringBootTest
@RunWith(SpringRunner.class)
public class CarCategoryServiceImplTest {

    @Autowired
    private CarCategoryServiceImpl carCategoryService;
    @Test
    public void findOne() {

        CarCategory result = carCategoryService.findOne(1);
        System.out.println(result);
        Assert.assertNotNull(result);
    }

    @Test
    public void save() {
        CarCategory carCategory = new CarCategory();
        carCategory.setCategoryType(3);
        carCategory.setCategoryName("test3");

        CarCategory result = carCategoryService.save(carCategory);
        Assert.assertNotNull(result);

    }

    @Test
    public void findAll() {

        List<CarCategory> result = carCategoryService.findAll();
        Assert.assertNotEquals(0,result.size());
        result.stream().forEach(c-> {
            System.out.println(c);
        });
    }

   /* @Test
    public void findByCarCategoryTypeIn() {

        List<CarCategory> list = carCategoryService.findByCarCategoryTypeIn(Arrays.asList(1));
        Assert.assertNotEquals(0,list.size());
        list.stream().forEach(c->{
            System.out.println(c);
        });
    }*/
}