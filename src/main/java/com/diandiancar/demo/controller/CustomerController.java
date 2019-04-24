package com.diandiancar.demo.controller;

import com.diandiancar.demo.entity.Customer;
import com.diandiancar.demo.form.CustomerForm;
import com.diandiancar.demo.repository.CustomersRepository;
import com.diandiancar.demo.service.CustomerService;
import com.diandiancar.demo.service.impl.CustomerServiceImpl;
import com.diandiancar.demo.util.MD5Util;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.Map;

@Controller
@RequestMapping("/customer")
public class CustomerController {


    @Autowired
    private CustomerService customerService;

    @Autowired
    private CustomersRepository customersRepository;

    @PostMapping("/login")
    public ModelAndView login(@Valid CustomerForm customerForm,
                              BindingResult bindingResult,
                              HttpServletRequest request,
                              Map<String, Object> map) {

        if (bindingResult.hasErrors()) {
            map.put("msg", bindingResult.getFieldError().getDefaultMessage());
            map.put("url", "/diandiancar/customer/toLogin");
            return new ModelAndView("/common/error", map);
        }

        String trimUserName = StringUtils.trimAllWhitespace(customerForm.getUserName());
        String trimPassword = StringUtils.trimAllWhitespace(customerForm.getPassword());

        if (StringUtils.isEmpty(trimUserName) || StringUtils.isEmpty(trimPassword)) {
            map.put("msg", "用户名或密码为空");
            map.put("url", "/diandiancar/customer/toLogin");
            return new ModelAndView("/common/error", map);
        }

        Customer customer = new Customer();
        BeanUtils.copyProperties(customerForm, customer);
        customer.setPassword(MD5Util.getMD5(trimPassword));
        Customer resultCustomer = customerService.findCustomerByUserNameAndPassword(customer);
        if (resultCustomer == null) {
            map.put("msg", "该用户不存在");
            map.put("url", "/diandiancar/customer/toLogin");
            return new ModelAndView("/common/error", map);
        }

        request.getSession().setAttribute("customer", resultCustomer);
        map.put("url", "/diandiancar/customer/index");
        return new ModelAndView("/common/success", map);

    }

    @PostMapping("/register")
    public ModelAndView register(@Valid CustomerForm customerForm,
                                 BindingResult bindingResult,
                                 Map<String, Object> map) {

        if (bindingResult.hasErrors()) {
            map.put("msg", bindingResult.getFieldError().getDefaultMessage());
            map.put("url", "/diandiancar/customer/toRegister");
            return new ModelAndView("/common/error", map);
        }
        String trimUserName = StringUtils.trimAllWhitespace(customerForm.getUserName());
        String trimPassword = StringUtils.trimAllWhitespace(customerForm.getPassword());
        String trimConfirmPassword = StringUtils.trimAllWhitespace(customerForm.getConfirmPassword());

        if (StringUtils.isEmpty(trimUserName) || StringUtils.isEmpty(trimPassword)
                || StringUtils.isEmpty(trimConfirmPassword)) {
            map.put("msg", "用户名或密码为空");
            map.put("url", "/diandiancar/customer/toRegister");
            return new ModelAndView("/common/error", map);
        }
        if (!trimPassword.equals(trimConfirmPassword)) {
            map.put("msg", "密码不相同，请重试");
            map.put("url", "/diandiancar/customer/toRegister");
            customerForm.setPassword("");
            customerForm.setConfirmPassword("");
            map.put("customerForm", customerForm);
            return new ModelAndView("/common/error", map);
        }

        Customer customer = new Customer();
        BeanUtils.copyProperties(customerForm, customer);
        customer.setUserName(trimUserName);
        customer.setPassword(MD5Util.getMD5(trimPassword));

        Customer resultCustomerByUserName = customerService.findCustomerByUserName(customer.getUserName());
        if (resultCustomerByUserName != null) {
            map.put("msg", "该用户已存在！");
            map.put("url", "/diandiancar/customer/toRegister");
            return new ModelAndView("/common/error", map);
        }
        Customer resultCustomerByTelNum = customerService.findCustomerByTelNum(customer.getTelNum());
        if (resultCustomerByTelNum != null) {
            map.put("msg", "该用户已存在！");
            map.put("url", "/diandiancar/customer/toRegister");
            return new ModelAndView("/common/error", map);
        }

        customerService.create(customer);
        map.put("url", "/diandiancar/customer/toLogin");
        return new ModelAndView("/common/success", map);

    }

    @RequestMapping("/logout")
    public ModelAndView logout(HttpServletRequest request,
                               Map<String, Object> map) {

        request.getSession().removeAttribute("customer");

        map.put("url", "/diandiancar/customer/index");
        return new ModelAndView("/common/success", map);
    }

    @RequestMapping("/index")
    public ModelAndView index(HttpServletRequest request) {
        System.out.println("index");

        return new ModelAndView("/customer/index");
    }

    @RequestMapping("/toLogin")
    public ModelAndView toLogin() {
        System.out.println("in");
        return new ModelAndView("/customer/login");
    }

    @RequestMapping("/toRegister")
    public ModelAndView toRegister() {
        System.out.println("in");
        return new ModelAndView("/customer/register");
    }

    @RequestMapping("/identity/toUpdatePassword")
    public ModelAndView toUpdatePassword() {
        System.out.println("in");
        return new ModelAndView("/customer/uploadPassword");
    }
    //跳转到personal_center.jsp
    @RequestMapping("/toPersonalCenter")
    public ModelAndView personalCcenter() {

        return new ModelAndView("/customer/personal_center");
    }

    @PostMapping("/identity/updatePassword")
    public ModelAndView updatePassword(@Valid CustomerForm customerForm,
                               BindingResult bindingResult,
                               HttpServletRequest request,
                               Map<String, Object> map) {
        if (bindingResult.hasErrors()) {
            map.put("msg", bindingResult.getFieldError().getDefaultMessage());
            map.put("url", "/diandiancar/customer/toPersonalCenter");
            return new ModelAndView("/common/error", map);
        }
        Customer customer = (Customer) request.getSession().getAttribute("customer");
        String oldPassword = StringUtils.trimAllWhitespace(customerForm.getOldPassword());
        String password = StringUtils.trimAllWhitespace(customerForm.getPassword());
        String confirmPassword = StringUtils.trimAllWhitespace(customerForm.getConfirmPassword());

        if (!customer.getPassword().equals(oldPassword)) {
            map.put("msg", "原密码与旧密码不相同");
            map.put("url", "/diandiancar/customer/toPersonalCenter");
            return new ModelAndView("/common/error", map);
        }

        if (!password.equals(confirmPassword)) {
            map.put("msg", "两次密码不相同");
            map.put("url", "/diandiancar/customer/toPersonalCenter");
            return new ModelAndView("/common/error", map);
        }
        customer.setPassword(password);
        customersRepository.save(customer);
        request.getSession().setAttribute("customer",customer);
        map.put("url", "/diandiancar/customer/toPersonalCenter");
        return new ModelAndView("/common/success", map);

    }

    //修改个人信息
    @PostMapping("/identity/update")
    public ModelAndView update(@Valid CustomerForm customerForm,
                               BindingResult bindingResult,
                               HttpServletRequest request,
                               Map<String, Object> map) {

        if (bindingResult.hasErrors()) {
            map.put("msg", bindingResult.getFieldError().getDefaultMessage());
            map.put("url", "/diandiancar/customer/toPersonalCenter");
            return new ModelAndView("/common/error", map);
        }
        Customer customer = (Customer) request.getSession().getAttribute("customer");

        if (customer == null) {
            map.put("msg", "请登录");
            map.put("url", "/diandiancar/customer/toLogin");
            return new ModelAndView("/common/error", map);
        }

        String driverLicenseID = StringUtils.trimAllWhitespace(customerForm.getDriverLicenseID());
        String nickname = StringUtils.trimAllWhitespace(customerForm.getNickname());
        String telNum = StringUtils.trimAllWhitespace(customerForm.getTelNum());

        if (StringUtils.isEmpty(nickname)
                || !CustomerServiceImpl.isPhone(telNum)) {
            map.put("msg", "参数不能为空");
            map.put("url", "/diandiancar/customer/toPersonalCenter");
            return new ModelAndView("/common/error", map);
        }

        customer.setDriverLicenseID(driverLicenseID);
        customer.setNickname(nickname);
        customer.setTelNum(telNum);
        customersRepository.save(customer);
        request.getSession().setAttribute("customer",customer);
        map.put("url", "/diandiancar/customer/toPersonalCenter");
        return new ModelAndView("/common/success", map);
    }

    @GetMapping("/check_username")
    @ResponseBody
    public String checkUserName(@RequestParam(value = "userName",required = true)String userName){

        if(StringUtils.isEmpty(userName)){
            return "请输入用户名";
        }
        Customer customerByUserName = customerService.findCustomerByUserName(userName);
        if(customerByUserName==null){
            return "该用户不存在";
        }
        return "用户名正确";
    }

}
