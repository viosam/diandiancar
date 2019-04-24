package com.diandiancar.demo.form;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class CarInfoForm {

    private String carId;

    //车辆名称
    private String carName;

    //车辆类型编号
    private Integer categoryType;

    //可载人数
    private Integer capacity;

    //发动机号
    private Integer kilometers;

    //生产商
    private String producer;

    //每天租金
    private BigDecimal rentPrice;

    //押金
    private BigDecimal deposit;

    //生产日期
    private String productTime;

    //车辆状态（0已租出，1可租）
    private Integer status;

    //车辆图标
    private String carIcon;

    //描述信息
    private String carDescription;

}
