package com.diandiancar.demo.entity;


import lombok.Data;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.util.Date;

@Table(name = "diandiancar_repair")
@Entity
@DynamicUpdate
@Data
public class Repair {

    @Id
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

    //创建时间
    private Date createTime;

}
