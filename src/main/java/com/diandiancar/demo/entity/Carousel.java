package com.diandiancar.demo.entity;


import com.diandiancar.demo.enums.CarStatusEnum;
import com.diandiancar.demo.enums.CarouselDeleteEnum;
import com.diandiancar.demo.util.EnumUtil;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

@Table(name = "diandiancar_admin_carousel")
@Entity
@DynamicUpdate
@Data
public class Carousel {

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

    private Date createTime;

    private Date updateTime;

    @JsonIgnore
    public CarouselDeleteEnum getCarouselDeleteEnum(){
        return EnumUtil.getByCode(deleted, CarouselDeleteEnum.class);
    }




}
