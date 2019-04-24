package com.diandiancar.demo.entity;

import com.diandiancar.demo.enums.CarDeleteEnum;
import com.diandiancar.demo.enums.CarStatusEnum;
import com.diandiancar.demo.util.EnumUtil;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.util.Date;

@Table(name = "diandiancar_car_info")
@Entity
@DynamicUpdate
@Data
public class CarInfo {

    @Id
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
    private Date productTime;

    //车辆状态（0已租出，1可租）
    private Integer status;

    //是否删除（0未删除，1,已删除）
    private Integer deleted;

    //车辆图标
    private String carIcon;

    //描述信息
    private String carDescription;

    //创建时间
    private Date createTime;

    //更新时间
    private Date updateTime;

    @JsonIgnore
    public CarStatusEnum getCarStatusEnum(){
        return EnumUtil.getByCode(status, CarStatusEnum.class);
    }

    @JsonIgnore
    public CarDeleteEnum getCarDeleteEnum(){
        return EnumUtil.getByCode(deleted, CarDeleteEnum.class);
    }

}
