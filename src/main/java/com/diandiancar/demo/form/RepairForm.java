package com.diandiancar.demo.form;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class RepairForm {

    private String id;

    //故障车id
    private String carId;

    //描述
    private String description;

    //维修金额
    private BigDecimal money;

    //维修部位
    private String repairPart;

    //维修状态（0提交维修信息，1维修完结，2维修失败）
    private Integer status;

    //事故车图片
    private String icons;

}
