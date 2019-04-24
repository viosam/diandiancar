package com.diandiancar.demo.VO;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 车辆详情
 */
@Data
public class CarInfoVO {

    private String carId;

    //车辆名称
    private String carName;

    //可载人数
    private Integer capacity;

    //发动机号
    private String kilometers;

    //生产商
    private String producer;

    //每天租金
    private BigDecimal rentPrice;

    //生产日期
    private Date productTime;

    //车辆图标
    private String carIcon;

    //描述信息
    private String carDescription;
}
