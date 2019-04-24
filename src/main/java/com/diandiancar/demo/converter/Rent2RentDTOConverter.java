package com.diandiancar.demo.converter;

import com.diandiancar.demo.dto.RentDTO;
import com.diandiancar.demo.entity.Rent;
import org.springframework.beans.BeanUtils;

import java.util.List;
import java.util.stream.Collectors;

public class Rent2RentDTOConverter {

    public static RentDTO convert(Rent rent){

        RentDTO rentDTO = new RentDTO();
        BeanUtils.copyProperties(rent,rentDTO);

        return rentDTO;
    }

    public static List<RentDTO> convert(List<Rent> rentList) {

        return rentList.stream().map(e->convert(e)).collect(Collectors.toList());
    }
}
