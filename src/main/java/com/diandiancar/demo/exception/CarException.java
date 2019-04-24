package com.diandiancar.demo.exception;

import com.diandiancar.demo.enums.ResultEnum;
import lombok.Getter;

@Getter
public class CarException extends RuntimeException {

    private Integer code;

    public CarException(ResultEnum resultEnum){

        super(resultEnum.getMessage());
        this.code = resultEnum.getCode();
    }

    public CarException(Integer code, String message){
        super(message);
        this.code = code;
    }

}
