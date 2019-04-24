package com.diandiancar.demo.entity;

import lombok.Data;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.math.BigDecimal;

@Table(name = "diandiancar_credit_level")
@Entity
@DynamicUpdate
@Data
public class CreditLevel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    //信用等级（数字越高等级越高）
    private Integer creditLevel;

    //定金
    private BigDecimal bookMoney;

}
