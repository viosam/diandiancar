package com.diandiancar.demo.service.impl;

import com.diandiancar.demo.entity.CarInfo;
import com.diandiancar.demo.entity.Carousel;
import com.diandiancar.demo.enums.CarDeleteEnum;
import com.diandiancar.demo.enums.CarStatusEnum;
import com.diandiancar.demo.enums.CarouselDeleteEnum;
import com.diandiancar.demo.enums.ResultEnum;
import com.diandiancar.demo.exception.CarException;
import com.diandiancar.demo.repository.CarInfoRepository;
import com.diandiancar.demo.repository.CarouselRepository;
import com.diandiancar.demo.service.CarouselService;
import com.diandiancar.demo.util.KeyUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CarouselServiceImpl implements CarouselService {

    @Autowired
    private CarouselRepository carouselRepository;

    @Autowired
    private CarInfoRepository carInfoRepository;

    @Override
    public List<Carousel> findAll() {
        return carouselRepository.findAll();
    }

    @Override
    public Carousel save(Carousel carousel) {

        if (carousel == null) {
            throw new CarException(ResultEnum.PARAM_ERROR);
        }
        if (StringUtils.isEmpty(carousel.getId())) {
            carousel.setId(KeyUtil.getUniqueKey());
        }
        if (StringUtils.isEmpty(carousel.getDeleted())) {
            carousel.setDeleted(CarouselDeleteEnum.NOT_DELETE.getCode());
        }
        Carousel result = carouselRepository.save(carousel);
        return result;
    }

    @Override
    public List<Carousel> findByDelete(Integer deleted) {
        return carouselRepository.findByDeleted(deleted);
    }

    @Override
    public List<Carousel> findRentable() {
        List<Carousel> carouselList = carouselRepository.findByDeleted(CarouselDeleteEnum.NOT_DELETE.getCode());
        List<Carousel> resultCarouselList = new ArrayList<>();
        for (Carousel carousel:carouselList) {
            CarInfo byCarIdAndStatus = carInfoRepository.findByCarIdAndStatus(carousel.getCarId(), CarStatusEnum.NOT_RENT.getCode());
            if(byCarIdAndStatus!=null){
                resultCarouselList.add(carousel);
            }
        }
        return resultCarouselList;
    }

    @Override
    public Carousel findById(String id) {
        return carouselRepository.findById(id).get();
    }

    @Override
    public Carousel delete(String id) {

        Carousel carousel = carouselRepository.findById(id).get();

        if (carousel == null) {
            throw new CarException(ResultEnum.CAROUSEL_NOT_EXIST);
        }
        if (carousel.getDeleted() == CarouselDeleteEnum.DELETED.getCode()) {
            throw new CarException(ResultEnum.CAROUSEL_DELETE_ERROR);
        }

        carousel.setDeleted(CarDeleteEnum.DELETED.getCode());
        return carouselRepository.save(carousel);
    }
}
