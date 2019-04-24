package com.diandiancar.demo.entity;

import lombok.Data;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;

@Table(name = "diandiancar_city")
@Entity
@DynamicUpdate
@Data
public class City {

    @Id
    private String id;

    //城市
    private String city;

    //省份序号
    private Integer provinceNum;

}
