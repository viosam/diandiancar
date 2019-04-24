package com.diandiancar.demo.repository;

import com.diandiancar.demo.entity.AdminInfo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AdminRepository extends JpaRepository<AdminInfo, String> {

    AdminInfo findByAdminNameAndPassword(String adminName,String password);
}
