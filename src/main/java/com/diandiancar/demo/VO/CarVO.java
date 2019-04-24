package com.diandiancar.demo.VO;

import lombok.Data;

import java.util.List;

/**
 * 以类型分组的车辆VO
 */
@Data
public class CarVO {

    private String carCategoryName;

    private Integer carCategoryType;

    private List<CarInfoVO> carInfoVOList;

    public List<CarInfoVO> getCarInfoVOList(){
        return this.carInfoVOList;
    }
}
