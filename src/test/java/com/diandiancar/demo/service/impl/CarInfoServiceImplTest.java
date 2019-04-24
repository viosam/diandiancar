package com.diandiancar.demo.service.impl;

import com.diandiancar.demo.entity.CarInfo;
import com.diandiancar.demo.repository.CarInfoRepository;
import com.diandiancar.demo.service.impl.CarInfoServiceImpl;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@SpringBootTest
@RunWith(SpringRunner.class)
public class CarInfoServiceImplTest {

    @Autowired
    private CarInfoServiceImpl carInfoService;

    @Autowired
    private CarInfoRepository carInfoRepository;
    private String carId = "001";

    @Test
    public void findById() {

        CarInfo result = carInfoService.findById(carId);

        Assert.assertNotNull(result);
    }

    @Test
    public void findAll() {
        PageRequest pageRequest = PageRequest.of(1, 2);
        Page<CarInfo> page = carInfoService.findAll(pageRequest);

//        System.out.println(page.getTotalElements());
        System.out.println(page.getContent());
        page.stream().forEach(c->{
            System.out.println(c.getCarName());
        });
        Assert.assertNotEquals(0,page.getTotalElements());
    }

   /* @Test
    public void like(){
        PageRequest pageRequest = PageRequest.of(1, 2);
        Page<CarInfo> page = carInfoRepository.findByCarNameLikeOrderByCreateTimeDesc(pageRequest);

        System.out.println(page.getContent());
        page.stream().forEach(c->{
            System.out.println(c.getCarName());
        });
        Assert.assertNotEquals(0,page.getTotalElements());

    }*/

    @Test
    public void findRentableAll(){
        List<CarInfo> rentableAll = carInfoService.findRentableAll();
        for (CarInfo c: rentableAll) {
            System.out.println(c);
        }
    }

    @Test
    public void save() {

        CarInfo carInfo = new CarInfo();
        carInfo.setCarId("122");
        carInfo.setStatus(1);
        carInfo.setCapacity(4);
        carInfo.setCarDescription("testcar描述");
        carInfo.setCarIcon("http://carIcon");
        carInfo.setCarName("testCarName中文");
        carInfo.setCategoryType(1);
        carInfo.setProducer("testProducer");
        carInfo.setKilometers(12334);
        carInfo.setDeleted(0);
        carInfo.setProductTime(new Date());
        carInfo.setRentPrice(new BigDecimal(20));

        CarInfo result = carInfoService.save(carInfo);
        Assert.assertNotNull(result);
    }

    @Test
    public void alreadyRenting() {

        CarInfo carInfo = carInfoService.alreadyRenting(carId);
        Assert.assertEquals(new Integer(0),carInfo.getStatus());
    }

    @Test
    public void notRented() {

        CarInfo carInfo = carInfoService.notRented(carId);
        Assert.assertEquals(new Integer(1),carInfo.getStatus());
    }

}