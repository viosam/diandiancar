package com.diandiancar.demo.dto;

import com.diandiancar.demo.enums.BookStatusEnum;
import com.diandiancar.demo.enums.PayBookStatusEnum;
import com.diandiancar.demo.util.EnumUtil;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
//如果某个字段为空，则不返回该字段
@JsonInclude(JsonInclude.Include.NON_NULL)
public class BookDTO {

    //bookid
    private String id;

    //预定车的id
    private String carID;

    //用户id
    private String customerID;

    //预定开始日
    private Date bookBeginDate;

    //预定结束日
    private Date bookEndDate;

    //总租用时间
    private Integer bookSumDate;

    //是否购买保险
    private Integer carInsurance;

    //创建时间
    private Date createTime;

    //预约状态（0为提交预约，1为预约成功，2为取消预约）
    private Integer status;

    //支付状态（0未支付（默认））
    private Integer payStatus;

    //定金
    private BigDecimal earnestMoney;

    //预约的门店
    private String fromShop;

    @JsonIgnore
    public PayBookStatusEnum getPayBookStatusEnum(){
        return EnumUtil.getByCode(payStatus, PayBookStatusEnum.class);
    }

    @JsonIgnore
    public BookStatusEnum getBookStatusEnum(){
        return EnumUtil.getByCode(status, BookStatusEnum.class);
    }


}
