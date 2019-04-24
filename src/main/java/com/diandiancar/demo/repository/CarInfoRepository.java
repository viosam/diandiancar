package com.diandiancar.demo.repository;

import com.diandiancar.demo.entity.CarInfo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface CarInfoRepository extends JpaRepository<CarInfo, String> {

    Page<CarInfo> findAllByOrderByCreateTimeDesc(Pageable pageable);

    List<CarInfo> findAllByOrderByCreateTimeDesc();

    Page<CarInfo> findByCarNameLike(String carName, Pageable pageable);

    List<CarInfo> findByCarNameLike(String carName);

    List<CarInfo> findByStatusAndDeleted(Integer carStatus, Integer delete);

    Page<CarInfo> findByStatus(Integer status,Pageable pageable);

    List<CarInfo> findByCategoryType(Integer categoryType);

    CarInfo findByCarIdAndStatus(String carId,Integer status);

    //批量更新categoryType
    @Modifying
    @Transactional
    @Query("update CarInfo c set c.categoryType=:categoryType where c.carId in (:carIds)")
    int updateCategoryByCarIds(@Param("categoryType") Integer categoryType, @Param("carIds") List<String> carIds);

}
