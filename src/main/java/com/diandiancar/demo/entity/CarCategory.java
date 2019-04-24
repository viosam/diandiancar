package com.diandiancar.demo.entity;

import lombok.Data;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.util.Date;

@Table(name = "diandiancar_car_category")
@Entity
@DynamicUpdate
@Data
public class CarCategory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer categoryId;

    //类型名称
    private String categoryName;

    //类型编号(唯一)
    private Integer categoryType;

    //创建时间
    private Date createTime;

    //更新时间
    private Date updateTime;
}
