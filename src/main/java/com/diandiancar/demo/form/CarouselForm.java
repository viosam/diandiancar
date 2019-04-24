package com.diandiancar.demo.form;


import lombok.Data;

import javax.persistence.Id;

@Data
public class CarouselForm {

    @Id
    private String id;

    //对应车辆Id
    private String carId;

    //图片地址
    private String picUrl;

    //描述
    private String description;

    //是否删除（0否1是）
    private Integer deleted;

}
