package com.diandiancar.demo.dto;

import com.diandiancar.demo.enums.RepairStatusEnum;
import com.diandiancar.demo.util.EnumUtil;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
//如果某个字段为空，则不返回该字段
@JsonInclude(JsonInclude.Include.NON_NULL)
public class RepairDTO {

    private String id;

    //故障车id
    private String carId;

    //描述
    private String description;

    //维修金额
    private BigDecimal money;

    //维修部位
    private String repairPart;

    //维修状态（0提交维修信息，1维修完结，2维修失败）
    private Integer status;

    //事故车图片
    private String icons;

    //创建时间
    private Date createTime;

    @JsonIgnore
    public RepairStatusEnum getRepairStatusEnum(){
        return EnumUtil.getByCode(status, RepairStatusEnum.class);
    }


}
