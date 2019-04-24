package com.diandiancar.demo.enums;

import lombok.Getter;

//租车状态（0为提交，1为完结，2为取消）
@Getter
public enum RentStatusEnum implements CodeEnum {

    SUBMIT(0, "提交租车信息"),
    FINISH(1, "租车完结"),
    CANCEL(2,"取消预约"),
//    CANCEL(2,"已取消")
    ;
    private Integer code;
    private String message;

    RentStatusEnum(Integer code, String message) {

        this.code = code;
        this.message = message;
    }

}
