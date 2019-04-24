package com.diandiancar.demo.form;

import lombok.Data;

@Data
public class CustomerForm {

    //用户名
    private String userName;

    //旧密码
    private String oldPassword;

    //密码
    private String password;

    //确认密码
    private String confirmPassword;

    //昵称
    private String nickname;

    //手机号码
    private String telNum;

    //驾照号码
    private String driverLicenseID;
}
