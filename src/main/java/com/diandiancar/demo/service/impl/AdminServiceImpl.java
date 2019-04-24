package com.diandiancar.demo.service.impl;

import com.diandiancar.demo.entity.AdminInfo;
import com.diandiancar.demo.enums.ResultEnum;
import com.diandiancar.demo.exception.CarException;
import com.diandiancar.demo.repository.AdminRepository;
import com.diandiancar.demo.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
public class AdminServiceImpl implements AdminService {

    @Autowired
    private AdminRepository repository;

    @Override
    public AdminInfo findByAdminNameAndPassword(AdminInfo adminInfo) {


        if (adminInfo == null || StringUtils.isEmpty(adminInfo.getAdminName()) || StringUtils.isEmpty(adminInfo.getPassword())) {
            throw new CarException(ResultEnum.PARAM_ERROR);
        }
        String adminName = adminInfo.getAdminName();
        String password = adminInfo.getPassword();
        return repository.findByAdminNameAndPassword(adminName,password);
    }
}
