package com.diandiancar.demo.service;

import com.diandiancar.demo.VO.StatisticsCarInOutLib;
import com.diandiancar.demo.dto.RentDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface RentService {

    //管理员查找所有人的租借记录
    Page<RentDTO> findAll(Pageable pageable);

    //管理员按月份统计租借记录
    Page<RentDTO> findByMonth(Integer month,Pageable pageable);

    //管理员车辆出入库状态查找
    StatisticsCarInOutLib findByCarStatus(Integer status, Pageable pageable);

    //用户查找租借列表
    List<RentDTO> findByCustomerId(String customerId);

    //查找单个记录
    RentDTO findOne(String rentId);

    //查找单个记录
    RentDTO findByIdAndCustomerId(String rentId,String customerId);

    //创建租车
    RentDTO create(RentDTO rentDTO) throws Exception;

    //支付押金
    RentDTO paidDeposit(RentDTO rentDTO);

    //支付租金
    RentDTO paidRent(RentDTO rentDTO) throws Exception;

    //管理员租车完结
    RentDTO finish(RentDTO rentDTO)throws Exception;

    //退还押金
    RentDTO refundDeposit(RentDTO rentDTO);

    //取消租车
    RentDTO cancel(RentDTO rentDTO);

}
