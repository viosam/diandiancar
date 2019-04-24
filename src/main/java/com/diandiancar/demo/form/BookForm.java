package com.diandiancar.demo.form;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class BookForm {

    //bookid
    private String id;

    //预定车的id
    private String carID;

    //用户id
    private String customerID;

    //预定开始日
    private String bookBeginDate;

    //预定结束日
    private String bookEndDate;

    //是否购买保险
    private Integer carInsurance;

    //定金
    private BigDecimal earnestMoney;

    //预约的门店
    private String fromShop;
}
