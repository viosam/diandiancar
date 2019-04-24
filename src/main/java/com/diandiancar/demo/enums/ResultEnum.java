package com.diandiancar.demo.enums;


import lombok.Getter;

@Getter
public enum ResultEnum implements CodeEnum {

    SUCCESS(0, "成功"),

    PARAM_ERROR(1, "参数错误"),
    FILE_UPLOAD_ERROR(10,"文件上传失败"),

    //car的返回码从1000开始
    CAR_NOT_EXIST(1000, "该车辆不存在"),
    CAR_RENTED(1001,"车辆已借出"),
    CAR_UPDATE(1002,"车辆信息更新失败"),
    CAR_DELETE_STATUS_FALSE(1003,"车辆删除的状态错误"),
    CAR_USED(1003,"车辆正在使用"),


    //book的返回码从2000开始
    BOOK_NOT_EXIST(2000, "预约单号不存在"),
    BOOK_STATUS_ERROR(2001, "预约状态错误"),
    BOOK_UPDAT_FAIL(2002, "预约状态更新失败"),
    BOOK_CANCEL_SUCCESS(2003,"预约取消成功"),
    BOOK_FINISH_SUCCESS(2004,"预约完结成功"),
    BOOK_NOT_FOUND(2005,"找不到预约信息"),

    //rent的返回码从3000开始
    RENT_NOT_EXIST(3000, "租借单号不存在"),
    RENT_STATUS_ERROR(3001, "租借状态错误"),
    RENT_UPDATE_FALL(3002, "租借状态更新失败"),
    RENT_CANCEL_SUCCESS(3003,"租借单取消成功"),
    RENT_FINISH_SUCCESS(3004,"租借单完结成功"),
    RENT_NOT_FOUND(3005,"找不到租借信息"),
    RENT_TIME_HAS_ERROR(3006,"租借时间有误"),
    PAY_RENT_ERROR(3007,"支付租金错误"),

    //账号、手机号的返回码从4000开始
    PHONE_NUM_ERROR(4000, "手机号码错误"),
    PHONE_NUM_USED(4001, "该号码已注册"),

    USER_NAME_USED(4100, "该账号已注册"),
    USER_NOT_EXIST(4101,"该用户不存在"),
    USER_FREEZE_STATUS_FALSE(4002,"用户冻结的状态错误"),
    USER_UNFREEZE_STATUS_FALSE(4003,"用户解冻的状态错误"),

    //轮播图从5000开始
    CAROUSEL_NOT_EXIST(5000,"轮播图不存在"),
    CAROUSEL_DELETE_ERROR(5001,"轮播图删除失败"),

    //维修从6000开始
    REPAIR_NOT_EXIST(6000,"维修单不存在"),
    REPAIR_STATUS_ERROR(6001,"维修状态错误"),
    REPAIR_UPDAT_FAIL(6002,"更新失败"),
    ;


    private Integer code;

    private String message;

    ResultEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

}
