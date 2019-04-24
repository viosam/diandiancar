package com.diandiancar.demo.service;

import com.diandiancar.demo.entity.CarInfo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface CarInfoService {

    CarInfo findById(String carId);

    //分页查找所有车辆信息
    Page<CarInfo> findAll(Pageable pageable);

    //查找所有可借车辆
    List<CarInfo> findRentableAll();

    Page<CarInfo> findByCarNameLike(String carName, Pageable pageable);
    List<CarInfo> findByCarNameLike(String carName);
    CarInfo save(CarInfo carInfo);

    CarInfo delete(String carId);

    //已出租
    CarInfo alreadyRenting(String carId);

    //未出租
    CarInfo notRented(String carId);

    List<CarInfo> findByCategoryType(Integer categoryType);

    int updateCategoryByCarIds(Integer categoryType, List<String> carIds);

    //管理员按月份统计新增车辆
    Page<CarInfo> findByMonth(Integer month,Pageable pageable);
    //管理员统计每个月新增车辆
    List<Integer> findAllByMonth();

}
