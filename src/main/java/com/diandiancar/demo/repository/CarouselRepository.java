package com.diandiancar.demo.repository;


import com.diandiancar.demo.entity.Carousel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CarouselRepository extends JpaRepository<Carousel, String> {

    List<Carousel> findByDeleted(Integer delete);

    Carousel findByCarId(String carId);
}
