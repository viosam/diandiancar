package com.diandiancar.demo.aspect;

import com.diandiancar.demo.exception.CustomerAuthorizeException;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

@Aspect
@Component
@Slf4j
public class CustomerAuthorizeAspect {

    @Pointcut("execution(public * com.diandiancar.demo.controller.Customer*.*(..))" +
            "&& !execution(public * com.diandiancar.demo.controller.CustomerController.*(..))"+
            "&& !execution(public * com.diandiancar.demo.controller.CustomerCarInfoController.*(..))")
    public void verify() {
    }

    @Before("verify()")
    public void doVerify() {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();

        Object customer = request.getSession().getAttribute("customer");
        if(customer==null){
            log.warn("[登陆校验] 用户未登录");
            throw new CustomerAuthorizeException();

        }
    }
}
