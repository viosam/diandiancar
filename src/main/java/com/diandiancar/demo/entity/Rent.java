package com.diandiancar.demo.entity;

import lombok.Data;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.util.Date;

@Table(name = "diandiancar_rent")
@Entity
@DynamicUpdate
@Data
public class Rent {

    //租车单号
    @Id
    private String id;

    //车辆id
    private String carId;

    //预约订单id
    private String bookId;

    //开始时间
    private Date beginDate;

    //结束时间
    private Date endDate;

    //总租用时间
    private Integer rentSumDate;

    //支付租金金额（减去定金）
    private BigDecimal paymentAmountRent;

    //支付押金金额
    private BigDecimal paymentAmountDeposit;

    //创建时间
    private Date createTime;

    //用户id
    private String customerId;

    //租车状态（0为提交，1为完结，2为取消）
    private Integer status;

    //是否购买保险
    private Integer carInsurance;

    //租借的门店
    private String fromShop;

    //归还的门店
    private String toShop;

    //支付租金状态（0未支付（默认））
    private Integer payRentStatus;

    //支付押金状态（0未支付（默认））
    private Integer payDepositStatus;
}
