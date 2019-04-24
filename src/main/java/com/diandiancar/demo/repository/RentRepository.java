package com.diandiancar.demo.repository;

import com.diandiancar.demo.entity.Rent;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Date;
import java.util.List;

public interface RentRepository extends JpaRepository<Rent, String> {

    List<Rent> findByCustomerIdOrderByCreateTimeDesc(String customerId);

    List<Rent> findAllByOrderByCreateTimeDesc();

    Page<Rent> findAllByOrderByCreateTimeDesc(Pageable pageable);

    Rent findByBookIdAndCustomerId(String bookId, String customerId);

    Rent findByIdAndCustomerId(String rentId, String customerId);

    Rent findByBookId(String bookId);

    Page<Rent> findByCreateTimeBetween(Date beginDate,Date endDate,Pageable pageable);

    Page<Rent> findByStatus(Integer status,Pageable pageable);

    Page<Rent> findByCarIdIn(List<String> carIds, Pageable pageable);

}
