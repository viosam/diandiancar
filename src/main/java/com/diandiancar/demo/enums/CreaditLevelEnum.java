package com.diandiancar.demo.enums;

import lombok.Getter;

@Getter
public enum  CreaditLevelEnum implements CodeEnum{

    LEVEL_PLATINUM(1,"铂金等级"),
    LEVEL_DIAMOND(2,"钻石等级");
    private Integer code;
    private String message;

    CreaditLevelEnum(Integer code, String message){
        this.code = code;
        this.message = message;
    }
}
