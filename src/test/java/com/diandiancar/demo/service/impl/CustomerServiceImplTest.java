package com.diandiancar.demo.service.impl;

import com.diandiancar.demo.entity.Customer;
import com.diandiancar.demo.enums.CustomerFreezedEnum;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@SpringBootTest
@RunWith(SpringRunner.class)
public class CustomerServiceImplTest {

    @Autowired
    CustomerServiceImpl customerService;

    @Test
    public void findCustomerByUserNameAndPassword() {
    }

    @Test
    public void findCustomerByTelNum() {
    }

    @Test
    public void create(){

        Customer customer = new Customer();
        customer.setDriverLicenseID("1101101111");
        customer.setNickname("testCustomer1");
        customer.setTelNum("13560000012");
        customer.setUserName("testUserName1");
        customer.setPassword("testPassword1");
        customer.setFreezed(CustomerFreezedEnum.NOT_FREEZED.getCode());
        Customer result = customerService.create(customer);

        Assert.assertNotNull(result);
    }

    @Test
    public void test() {
        String telNum = "13600000002";
        String regex = "^((13[0-9])|(14[5,7,9])|(15([0-3]|[5-9]))|(166)|(17[0,1,3,5,6,7,8])|(18[0-9])|(19[8|9]))\\d{8}$";
        if (telNum.length() != 11) {
            System.out.println("false"+"------"+telNum.length());
        } else {
            Pattern p = Pattern.compile(regex);
            Matcher m = p.matcher(telNum);
            boolean isMatch = m.matches();
            System.out.println(isMatch);
        }
    }

}