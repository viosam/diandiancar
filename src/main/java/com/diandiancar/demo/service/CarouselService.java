package com.diandiancar.demo.service;

import com.diandiancar.demo.entity.Carousel;

import java.util.List;

public interface CarouselService {

    List<Carousel> findAll();

    Carousel save(Carousel carousel);

    List<Carousel> findByDelete(Integer deleted);

    List<Carousel> findRentable();

    Carousel findById(String id);

    Carousel delete(String id);

}
