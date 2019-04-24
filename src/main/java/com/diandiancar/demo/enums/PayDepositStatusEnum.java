package com.diandiancar.demo.enums;

import lombok.Getter;

//支付押金状态
@Getter
public enum PayDepositStatusEnum implements CodeEnum{

    WAIT(0,"等待支付押金"),
    SUCCESS(1,"支付押金完成"),
    REFUNDING(2,"押金退款中"),
    REFUND_SUCCESSFULLY(3,"押金退款成功"),
    REFUND_FAIL(4,"押金退款失败"),
    PAID_FAIL(5,"押金支付失败"),
    ;
    private Integer code;
    private String message;

    PayDepositStatusEnum(Integer code, String message){
        this.code = code;
        this.message = message;
    }
}
