package com.diandiancar.demo.VO;

import com.diandiancar.demo.dto.RentDTO;
import lombok.Data;
import org.springframework.data.domain.Page;

//统计车辆出入库
@Data
public class StatisticsCarInOutLib {

    private Integer returned;
    private Integer notReturend;
    private Page<RentDTO> rentDTOPage;
}
