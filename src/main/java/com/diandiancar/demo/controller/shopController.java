package com.diandiancar.demo.controller;

import com.diandiancar.demo.entity.City;
import com.diandiancar.demo.entity.Province;
import com.diandiancar.demo.entity.Shop;
import com.diandiancar.demo.repository.CityRepository;
import com.diandiancar.demo.repository.ProvinceRepository;
import com.diandiancar.demo.repository.ShopRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/customer/shop")
@Slf4j
public class shopController {

    @Autowired
    ProvinceRepository provinceRepository;

    @Autowired
    CityRepository cityRepository;

    @Autowired
    ShopRepository shopRepository;

    @GetMapping("/toShop")
    public ModelAndView toShop(Map<String,Object>map) {
        System.out.println("in");
        List<Province> provinceList = provinceRepository.findAll();
        map.put("provinceList",provinceList);
        return new ModelAndView("/customer/shop",map);
    }

    @GetMapping("/province")
    @ResponseBody
    public List<Province> findProvince(){

        List<Province> provinceList = provinceRepository.findAll();
        return provinceList;
    }

    @GetMapping("/city")
    @ResponseBody
    public List<City> findCityByProvinceNum(@RequestParam(value = "provinceNum",required = true) Integer provinceNum){

        List<City> cityList = cityRepository.findByProvinceNum(provinceNum);

        return cityList;
    }

    @GetMapping("/shop")
    @ResponseBody
    public List<Shop> findShopByCity(@RequestParam(value = "city",required = true) String city){

        List<Shop> shopList = shopRepository.findByCity(city);

        return shopList;
    }
}
