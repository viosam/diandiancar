package com.diandiancar.demo.enums;

import lombok.Getter;

//是否买车险（0没，1有）
@Getter
public enum CarInsuranceEnum implements CodeEnum {

    NOT_INSURANCE(0,"无车险"),
    HAS_INSURANCE(1,"有车险"),
    ;

    private Integer code;
    private String message;

    CarInsuranceEnum(Integer code, String message){
        this.code = code;
        this.message = message;
    }
}
