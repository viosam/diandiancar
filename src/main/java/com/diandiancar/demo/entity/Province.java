package com.diandiancar.demo.entity;

import lombok.Data;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;

@Table(name = "diandiancar_province")
@Entity
@DynamicUpdate
@Data
public class Province {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    //省份
    private String province;

    //省份序号
    private Integer provinceNum;

}
