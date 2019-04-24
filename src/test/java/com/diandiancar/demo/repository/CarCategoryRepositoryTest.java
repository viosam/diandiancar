package com.diandiancar.demo.repository;

import com.diandiancar.demo.entity.CarCategory;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Optional;

@SpringBootTest
@RunWith(SpringRunner.class)
public class CarCategoryRepositoryTest {

    @Autowired
    private CarCategoryRepository repository;

    @Test
    public void save() {
        CarCategory carCategory = new CarCategory();

        carCategory.setCategoryName("test1name");
        carCategory.setCategoryType(1);
        CarCategory result = repository.save(carCategory);
    }

    @Test
    public void find() {
        Optional<CarCategory> result = repository.findById(1);
        System.out.println(result.get());
    }

   /* @Test
    public void findByCategoryTypeIn(){

        List<CarCategory> list = repository.findByCategoryTypeIn(Arrays.asList(1, 0));

        list.stream().forEach(c->{
            System.out.println(c.getCategoryName());
        });
        Assert.assertNotEquals(0,list.size());
    }*/

}