package com.diandiancar.demo.converter;

import com.diandiancar.demo.dto.RepairDTO;
import com.diandiancar.demo.entity.Repair;
import org.springframework.beans.BeanUtils;

import java.util.List;
import java.util.stream.Collectors;

public class Repair2RepairDTOConverter {

    public static RepairDTO convert(Repair repair){

        RepairDTO repairDTO = new RepairDTO();
        BeanUtils.copyProperties(repair,repairDTO);

        return repairDTO;
    }

    public static List<RepairDTO> convert(List<Repair> repairList){

        return repairList.stream().map(e->convert(e)).collect(Collectors.toList());
    }

}
