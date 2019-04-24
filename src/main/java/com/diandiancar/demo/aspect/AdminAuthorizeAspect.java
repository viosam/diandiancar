package com.diandiancar.demo.aspect;

import com.diandiancar.demo.exception.AdminAuthorizeException;
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
public class AdminAuthorizeAspect {

    @Pointcut("execution(public * com.diandiancar.demo.controller.Admin*.*(..))" +
            "&& !execution(public * com.diandiancar.demo.controller.AdminController.toLogin(..))" +
            "&& !execution(public * com.diandiancar.demo.controller.AdminController.login(..))")
    public void adminverify() {
    }

    @Before("adminverify()")
    public void doVerify() {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();

        Object customer = request.getSession().getAttribute("admin");
        if(customer==null){
            log.warn("[登陆校验] 管理員未登录");
            throw new AdminAuthorizeException();

        }
    }
}
