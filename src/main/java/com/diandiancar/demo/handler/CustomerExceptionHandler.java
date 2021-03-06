package com.diandiancar.demo.handler;

import com.diandiancar.demo.VO.ResultVO;
import com.diandiancar.demo.exception.CarException;
import com.diandiancar.demo.exception.CustomerAuthorizeException;
import com.diandiancar.demo.util.ResultVOUtil;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

@ControllerAdvice
public class CustomerExceptionHandler {


    //拦截登录异常
    @ExceptionHandler(value = CustomerAuthorizeException.class)
    public ModelAndView handlerSellerAuthorizeException() {

        return new ModelAndView("redirect:"
                .concat("/customer/toLogin"));
    }

    @ExceptionHandler(value = CarException.class)
    @ResponseBody
    public ResultVO handlerSellerException(CarException e){
        return ResultVOUtil.error(e.getCode(),e.getMessage());

    }
}
