package com.diandiancar.demo.enums;

import lombok.Getter;

//是否冻结（0未冻结，1,已冻结）
@Getter
public enum CustomerFreezedEnum implements CodeEnum {

    NOT_FREEZED(0,"未冻结"),
    FREEZED(1,"已冻结"),
    ;

    private Integer code;
    private String message;

    CustomerFreezedEnum(Integer code, String message){
        this.code = code;
        this.message = message;
    }
}
