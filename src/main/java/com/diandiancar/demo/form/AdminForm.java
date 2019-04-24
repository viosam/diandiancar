package com.diandiancar.demo.form;

import lombok.Data;

@Data
public class AdminForm {

    //管理员账号
    private String adminName;

    //密码
    private String password;

    //确认密码
    private String confirmPassword;

    //管理员等级
    private String level;

    //昵称
    private String nickname;
}
