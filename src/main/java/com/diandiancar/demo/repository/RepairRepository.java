package com.diandiancar.demo.repository;

import com.diandiancar.demo.entity.Repair;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Date;
import java.util.List;


public interface RepairRepository extends JpaRepository<Repair, String> {


    Page<Repair> findAllByOrderByCreateTimeDesc(Pageable pageable);

    Page<Repair> findAllByCreateTimeBetween(Date beginDate, Date endDate,Pageable pageable);

    Page<Repair> findAllByCreateTimeAfter(Date beginDate,Pageable pageable);

    Page<Repair> findAllByCreateTimeBefore(Date endDate,Pageable pageable);
}
