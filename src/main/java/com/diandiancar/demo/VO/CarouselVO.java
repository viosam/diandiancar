package com.diandiancar.demo.VO;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 轮播图
 */
@Data
public class CarouselVO {

    private String id;

    //对应车辆的id
    private String car_id;

    //图片地址
    private Integer pic_url;

    //描述
    private String description;

}
