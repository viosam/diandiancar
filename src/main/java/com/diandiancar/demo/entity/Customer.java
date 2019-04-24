package com.diandiancar.demo.entity;

import com.diandiancar.demo.enums.CustomerFreezedEnum;
import com.diandiancar.demo.util.EnumUtil;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

@Table(name = "diandiancar_customer_info")
@Entity
@Data
public class Customer {

    @Id
    private String id;

    //用户名
    private String userName;

    //密码
    private String password;

    //昵称
    private String nickname;

    //信用等级
    private Integer creditLevel;

    //手机号码
    private String telNum;

    //是否冻结（0未冻结，1,已冻结）
    private Integer freezed;

    //驾照号码
    private String driverLicenseID;

    //创建时间
    private Date createTime;

    @JsonIgnore
    public CustomerFreezedEnum getCustomerFreezedEnum(){
        return EnumUtil.getByCode(freezed, CustomerFreezedEnum.class);
    }

}
