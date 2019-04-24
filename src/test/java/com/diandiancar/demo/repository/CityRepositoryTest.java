package com.diandiancar.demo.repository;

import com.diandiancar.demo.entity.City;
import com.diandiancar.demo.entity.Province;
import com.diandiancar.demo.entity.Shop;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@SpringBootTest
@RunWith(SpringRunner.class)
public class CityRepositoryTest {

    @Autowired
    ProvinceRepository provinceRepository;

    @Autowired
    CityRepository cityRepository;

    @Autowired
    ShopRepository shopRepository;

    @Test
    public void province(){
        Province province = provinceRepository.findById(1).get();
        System.out.println(province.getProvince());
        System.out.println("----------------");

//        City city = cityRepository.findById("1000011").get();
//        System.out.println(city.getCity());
        List<City> byProvinceNum = cityRepository.findByProvinceNum(10);
        for (City c: byProvinceNum) {

            System.out.println(c.getCity());
        }
        System.out.println("=============");

        List<Shop> shopList = shopRepository.findByCity("汕头");

        for (Shop s: shopList){
            System.out.println(s.getShop());
        }
    }
}