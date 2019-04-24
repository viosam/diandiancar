package com.diandiancar.demo.dto;

import com.diandiancar.demo.enums.CarInsuranceEnum;
import com.diandiancar.demo.enums.PayDepositStatusEnum;
import com.diandiancar.demo.enums.PayRentStatusEnum;
import com.diandiancar.demo.enums.RentStatusEnum;
import com.diandiancar.demo.util.EnumUtil;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class RentDTO {

    //租车单号
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

    //是否购买保险
    private Integer carInsurance;

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

    //支付租金状态（0未支付（默认））
    private Integer payRentStatus;

    //支付押金状态（0未支付（默认））
    private Integer payDepositStatus;

    //租借的门店
    private String fromShop;

    //归还的门店
    private String toShop;

    @JsonIgnore
    public CarInsuranceEnum getCarInsuranceEnum(){
        return EnumUtil.getByCode(carInsurance, CarInsuranceEnum.class);
    }

    @JsonIgnore
    public PayDepositStatusEnum getPayDepositStatusEnum(){
        return EnumUtil.getByCode(payDepositStatus, PayDepositStatusEnum.class);
    }


    @JsonIgnore
    public PayRentStatusEnum getPayRentStatusEnum(){
        return EnumUtil.getByCode(payRentStatus, PayRentStatusEnum.class);
    }

    @JsonIgnore
    public RentStatusEnum getRentStatusEnum(){
        return EnumUtil.getByCode(status, RentStatusEnum.class);
    }

}
