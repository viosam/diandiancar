package com.diandiancar.demo.controller;

import com.diandiancar.demo.entity.AdminInfo;
import com.diandiancar.demo.form.AdminForm;
import com.diandiancar.demo.service.AdminService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.Map;


@Controller
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private AdminService adminService;

    @PostMapping("/login")
    public ModelAndView login(@Valid AdminForm adminForm,
                              BindingResult bindingResult,
                              HttpServletRequest request,
                              Map<String,Object> map){

        if (bindingResult.hasErrors()) {
            map.put("msg", bindingResult.getFieldError().getDefaultMessage());
            map.put("url", "/diandiancar/admin/toLogin");
            return new ModelAndView("/common/error", map);
        }

        String trimAdminName = StringUtils.trimAllWhitespace(adminForm.getAdminName());
        String trimPassword = StringUtils.trimAllWhitespace(adminForm.getPassword());

        if (StringUtils.isEmpty(adminForm.getAdminName())||StringUtils.isEmpty(adminForm.getPassword())) {
            map.put("msg", "用户名或密码为空");
            map.put("url", "/diandiancar/admin/toLogin");
            return new ModelAndView("/common/error", map);
        }

        AdminInfo adminInfo = new AdminInfo();

        adminInfo.setAdminName(trimAdminName);
        adminInfo.setPassword(trimPassword);
        AdminInfo resultAdminInfo = adminService.findByAdminNameAndPassword(adminInfo);
        if (resultAdminInfo==null){
            map.put("msg", "该用户不存在");
            map.put("url", "/diandiancar/admin/toLogin");
            return new ModelAndView("/common/error", map);
        }

        request.getSession().setAttribute("admin",resultAdminInfo);
        map.put("url", "/diandiancar/admin/index");
        return new ModelAndView("/common/success", map);

    }

    @RequestMapping("/logout")
    public ModelAndView logout(HttpServletRequest request,
                               Map<String,Object> map){

        request.getSession().removeAttribute("admin");

        map.put("url", "/diandiancar/admin/toLogin");
        return new ModelAndView("/common/success", map);
    }

    @RequestMapping("/toLogin")
    public ModelAndView toLogin(){
        System.out.println("in");
        return new ModelAndView("/admin/login");
    }

    @RequestMapping("/index")
    public ModelAndView index(){
        System.out.println("index");
        return new ModelAndView("/admin/index");
    }

}
