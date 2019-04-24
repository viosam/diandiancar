package com.diandiancar.demo.form;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
public class RentForm {
    //租车单号
    private String id;

    //车辆id
    private String carId;

    //预约订单id
    private String bookId;

    //开始时间
    private String beginDate;

    //结束时间
    private String endDate;

    //总租用时间
    private Integer rentSumDate;

    //支付租金金额（减去定金）
    private BigDecimal paymentAmountRent;

    //支付押金金额
    private BigDecimal paymentAmountDeposit;

    //用户id
    private String customerId;

    //是否购买保险
    private Integer carInsurance;

    //租借的门店
    private String fromShop;

    //归还的门店
    private String toShop;

}
