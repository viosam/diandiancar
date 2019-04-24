package com.diandiancar.demo.service.impl;

import com.diandiancar.demo.entity.CarInfo;
import com.diandiancar.demo.enums.CarDeleteEnum;
import com.diandiancar.demo.enums.CarStatusEnum;
import com.diandiancar.demo.enums.ResultEnum;
import com.diandiancar.demo.exception.CarException;
import com.diandiancar.demo.repository.CarInfoRepository;
import com.diandiancar.demo.service.CarInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

@Service
public class CarInfoServiceImpl implements CarInfoService {

    @Autowired
    private CarInfoRepository repository;

    @Override
    public CarInfo findById(String carId) {
        return repository.findById(carId).get();
    }

    @Override
    public Page<CarInfo> findAll(Pageable pageable) {
//        return repository.findAll(pageable);
        return repository.findAllByOrderByCreateTimeDesc(pageable);
    }

    @Override
    public List<CarInfo> findRentableAll() {
        return repository.findByStatusAndDeleted(CarStatusEnum.NOT_RENT.getCode(), CarDeleteEnum.NOT_DELETE.getCode());
    }

    @Override
    public Page<CarInfo> findByCarNameLike(String carName, Pageable pageable) {
        return repository.findByCarNameLike(carName, pageable);
    }

    @Override
    public List<CarInfo> findByCarNameLike(String carName) {
        return repository.findByCarNameLike(carName);

    }

    @Override
    public CarInfo save(CarInfo carInfo) {
        if (carInfo.getStatus() == null) {
            carInfo.setStatus(CarStatusEnum.NOT_RENT.getCode());
        }
        return repository.save(carInfo);
    }

    @Override
    public CarInfo delete(String carId) {
        CarInfo carInfo = repository.findById(carId).get();

        if (carInfo == null) {
            throw new CarException(ResultEnum.CAR_NOT_EXIST);
        }
        if (carInfo.getStatus() == CarStatusEnum.RENTED.getCode()) {
            throw new CarException(ResultEnum.CAR_USED);
        }
        if (carInfo.getDeleted() == CarDeleteEnum.DELETED.getCode()) {
            throw new CarException(ResultEnum.CAR_DELETE_STATUS_FALSE);
        }
        carInfo.setDeleted(CarDeleteEnum.DELETED.getCode());
        return repository.save(carInfo);
    }

    @Transactional
    @Override
    public CarInfo alreadyRenting(String carId) {

        CarInfo carInfo = repository.findById(carId).get();
        if (carInfo == null) {
            throw new CarException(ResultEnum.CAR_NOT_EXIST);
        }

        carInfo.setStatus(CarStatusEnum.RENTED.getCode());
        return repository.save(carInfo);
    }

    @Transactional
    @Override
    public CarInfo notRented(String carId) {
        CarInfo carInfo = repository.findById(carId).get();
        if (carId == null) {
            throw new CarException(ResultEnum.CAR_NOT_EXIST);
        }

        carInfo.setStatus(CarStatusEnum.NOT_RENT.getCode());
        return repository.save(carInfo);
    }

    @Override
    public List<CarInfo> findByCategoryType(Integer categoryType) {
        return repository.findByCategoryType(categoryType);
    }

    @Override
    public int updateCategoryByCarIds(Integer categoryType, List<String> carIds) {
        return repository.updateCategoryByCarIds(categoryType, carIds);
    }

    @Override
    public Page<CarInfo> findByMonth(Integer month, Pageable pageable) {

        //1.查找所有数据
        Page<CarInfo> byOrderByCreateTimeDesc = repository.findAllByOrderByCreateTimeDesc(pageable);
        List<CarInfo> content = byOrderByCreateTimeDesc.getContent();

        List<CarInfo> rentList = new ArrayList<>();

        //2. 比较记录的create_time 是否等于month
        Calendar calendar = Calendar.getInstance();
        for (CarInfo c : content) {
            calendar.setTime(c.getCreateTime());
            //calendar.get()获取的月份是从0开始
            int m = calendar.get(Calendar.MONTH) + 1;
            //3. 将数据保存到列表
            if (m == month) {
                rentList.add(c);
            }
        }

        PageImpl<CarInfo> rentDTOPage = new PageImpl<>(rentList, pageable, byOrderByCreateTimeDesc.getTotalElements());
        return rentDTOPage;
    }

    @Override
    public List<Integer> findAllByMonth() {
        List<CarInfo> byOrderByCreateTimeDesc = repository.findAllByOrderByCreateTimeDesc();
        Calendar calendar = Calendar.getInstance();
        List<Integer> monthNewCarlist = Arrays.asList(0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0);

        for (CarInfo c : byOrderByCreateTimeDesc) {
            calendar.setTime(c.getCreateTime());
            //calendar.get()获取的月份是从0开始
            int m = calendar.get(Calendar.MONTH);

            for (int i = 0; i < 12; i++) {
                //2. 比较记录的create_time 是否等于month
                if (m == i) {
                    //3. 将数据保存到列表
                    monthNewCarlist.set(i, monthNewCarlist.get(i) + 1);
                    break;
                }

            }

        }
        return monthNewCarlist;
    }
}
