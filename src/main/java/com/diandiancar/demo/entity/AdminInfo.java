package com.diandiancar.demo.entity;

import lombok.Data;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Table(name = "diandiancar_admin_info")
@Entity
@DynamicUpdate
@Data
public class AdminInfo {

    @Id
    private String id;

    //管理员账号
    private String adminName;

    //密码
    private String password;

    //管理员等级
    private String level;

    //昵称
    private String nickname;
}
