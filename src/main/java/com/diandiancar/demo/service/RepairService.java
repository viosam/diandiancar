package com.diandiancar.demo.service;

import com.diandiancar.demo.dto.RepairDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.text.ParseException;
import java.util.Date;
import java.util.List;

public interface RepairService {

    //管理员查找所有报修记录
    Page<RepairDTO> findAll(Pageable pageable);

    //管理员根据时间范围查找维修记录
    Page<RepairDTO> findByCreateTimeBetween(String beginDate, String endDate ,Pageable pageable) throws ParseException;

    //查找单个记录
    RepairDTO findOne(String repairId);

    //创建报修
    RepairDTO create(String carId);

    //取消报修
//    RepairDTO cancel(RepairDTO repairDTO);

    //预约完成
    RepairDTO finish(RepairDTO repairDTO);

    /**通过审核
     * tag:通过/取消
     * @param repairId
     * @param tag
     * @return
     */
    RepairDTO approved(String repairId,String tag);

}
