package com.diandiancar.demo.enums;

import lombok.Getter;

//车辆状态
@Getter
public enum CarStatusEnum implements CodeEnum {

    NOT_RENT(0, "未出租"),
    TO_BE_OUT_OF_THE_LIBRARY(1, "待出库"),
    RENTED(2, "已租出"),
    RETURNED(3,"已归还"),
    NO_RETURNED(4,"未归还"),

    REPAIRING(5,"维修中"),

    ;

    private Integer code;
    private String message;

    CarStatusEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

}
