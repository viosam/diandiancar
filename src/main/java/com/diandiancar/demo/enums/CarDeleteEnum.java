package com.diandiancar.demo.enums;

import lombok.Getter;

//是否删除（0未删除，1,已删除）
@Getter
public enum CarDeleteEnum implements CodeEnum {

    NOT_DELETE(0,"未删除"),
    DELETED(1,"已删除"),
    ;

    private Integer code;
    private String message;

    CarDeleteEnum(Integer code, String message){
        this.code = code;
        this.message = message;
    }
}
