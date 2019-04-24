package com.diandiancar.demo.entity;

import lombok.Data;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Table(name = "diandiancar_shop")
@Entity
@DynamicUpdate
@Data
public class Shop {

    @Id
    private String id;

    //店面
    private String shop;

    //城市
    private String city;

}
