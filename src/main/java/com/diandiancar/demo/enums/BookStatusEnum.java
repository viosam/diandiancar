package com.diandiancar.demo.enums;

import lombok.Getter;

//预约状态（0为提交预约，1为预约成功，2为取消预约）
@Getter
public enum BookStatusEnum implements CodeEnum {

    SUBMIT(0,"提交预约"),
    SUCCESS(1,"预约成功"),
    CANCEL(2,"已取消")
    ;

    private Integer code;
    private String message;

    BookStatusEnum(Integer code, String message){
        this.code = code;
        this.message = message;
    }
}
