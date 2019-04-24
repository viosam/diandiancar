package com.diandiancar.demo.enums;

import lombok.Getter;

//维修状态（0提交维修信息，1维修完结，2维修失败）
@Getter
public enum RepairStatusEnum implements CodeEnum {

    SUBMIT(0,"提交维修信息"),
    SUCCESS(1,"维修完结"),
    REPAIR(2,"维修中"),
    FAIL(3,"维修失败"),
    SCRAPPED(4,"报废"),
    ;

    private Integer code;
    private String message;

    RepairStatusEnum(Integer code, String message){
        this.code = code;
        this.message = message;
    }
}
