package com.diandiancar.demo.enums;

import lombok.Getter;

//支付租金状态
@Getter
public enum PayRentStatusEnum implements CodeEnum{

    /*WAIT(0,"等待支付"),
    SUCCESS(1,"支付完成"),
    REFUNDING(2,"退款中"),
    REFUND_SUCCESSFULLY(3,"退款成功"),
    FAIL(4,"支付失败"),*/

    WAIT(0,"等待支付租金"),
    SUCCESS(1,"租金支付完成"),
    REFUNDING(2,"租金退款中"),
    REFUND_SUCCESSFULLY(3,"租金退款成功"),
    REFUND_FAIL(4,"租金退款失败"),
    PAID_FAIL(5,"支付失败"),
    ;
    private Integer code;
    private String message;

    PayRentStatusEnum(Integer code, String message){
        this.code = code;
        this.message = message;
    }
}
