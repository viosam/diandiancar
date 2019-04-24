package com.diandiancar.demo.form;

import lombok.Data;

@Data
public class CarCategoryForm {

    private Integer categoryId;

    //类型名称
    private String categoryName;

    //类型编号(唯一)
    private Integer categoryType;

}
