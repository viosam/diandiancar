package com.diandiancar.demo.service;

import com.diandiancar.demo.entity.AdminInfo;
import com.diandiancar.demo.entity.Customer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface AdminService {

    AdminInfo findByAdminNameAndPassword(AdminInfo adminInfo);
}
