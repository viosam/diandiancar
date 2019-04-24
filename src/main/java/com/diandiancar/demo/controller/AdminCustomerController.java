package com.diandiancar.demo.controller;

import com.diandiancar.demo.entity.Customer;
import com.diandiancar.demo.enums.CustomerFreezedEnum;
import com.diandiancar.demo.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.Map;

@Controller
@RequestMapping("/admin/customer")
public class AdminCustomerController {

    @Autowired
    private CustomerService customerService;

    /**
     * 查看所有用户
     *
     * @param page
     * @param size
     * @param map
     * @return
     */
    @GetMapping("/list")
    public ModelAndView list(@RequestParam(value = "page", defaultValue = "1") Integer page,
                             @RequestParam(value = "size", defaultValue = "3") Integer size,
                             Map<String, Object> map) {
        PageRequest pageRequest = PageRequest.of(page - 1, size);
        Page<Customer> customerPage = customerService.findAll(pageRequest);

        map.put("customerPage", customerPage);
        map.put("currentPage", page);
        map.put("size", size);

        //因为search()也是跳转到list页面，所以判断若是search调用，则分页按钮跳转到Controller的search()
        map.put("searchTag","notSearch");
        return new ModelAndView("/admin/customer/customer-list", map);
    }

    /**
     * 查看已删除用户
     *
     * @param page
     * @param size
     * @param map
     * @return
     */
    @GetMapping("/freeze-list")
    public ModelAndView freezeList(@RequestParam(value = "page", defaultValue = "1") Integer page,
                                   @RequestParam(value = "size", defaultValue = "5") Integer size,
                                   Map<String, Object> map) {
        PageRequest pageRequest = PageRequest.of(page - 1, size);
        Page<Customer> customerPage = customerService.findByFreezed(CustomerFreezedEnum.FREEZED.getCode(), pageRequest);
        map.put("customerPage", customerPage);
        map.put("currentPage", page);
        map.put("size", size);
        map.put("searchTag","freezeList");
        return new ModelAndView("/admin/customer/customer-list", map);
    }


    @GetMapping("/search")
    public ModelAndView search(@RequestParam(value = "nickname", required = false) String nickname,
                               @RequestParam(value = "page", defaultValue = "1") Integer page,
                               @RequestParam(value = "size", defaultValue = "5") Integer size,
                               Map<String, Object> map) {

        PageRequest pageRequest = PageRequest.of(page - 1, size);
        if (StringUtils.isEmpty(nickname) || nickname == null) {
            Page<Customer> customerPage = customerService.findAll(pageRequest);
            if (customerPage == null) {
                map.put("msg", "查找结果为空，暂无用户信息！");
                map.put("url", "/diandiancar/admin/customer/list");
                return new ModelAndView("/common/error", map);
            }
            map.put("customerPage", customerPage);
            map.put("currentPage", page);
            map.put("size", size);
            map.put("searchTag","search");
        } else {
            Page<Customer> customerPage = customerService.
                    findByNicknameLike("%"+nickname+"%", pageRequest);
            if (customerPage == null) {
                map.put("msg", "查找结果为空，暂无用户信息！");
                map.put("url", "/diandiancar/admin/customer/list");
                return new ModelAndView("/common/error", map);
            }
            map.put("customerPage", customerPage);
            map.put("currentPage", page);
            map.put("size", size);
            map.put("searchTag","search");

        }
        //判断分页按钮跳转到Controller的search()
        map.put("searchTag","search");
        //分页按钮的请求参数
        if(nickname!=null){
            map.put("nickname",nickname);
        }
        return new ModelAndView("/admin/customer/customer-list", map);

    }

    @RequestMapping("/freeze")
    public ModelAndView freeze(@RequestParam("customerId") String customerId,
                               Map<String, Object> map) {

        try {
            customerService.freeze(customerId);
        } catch (Exception e) {
            map.put("msg", e.getMessage());
            map.put("url", "/diandiancar/admin/customer/list");
            return new ModelAndView("/common/error", map);
        }
        map.put("url", "/diandiancar/admin/customer/list");
        return new ModelAndView("/common/success", map);
    }

    @RequestMapping("/unfreeze")
    public ModelAndView unfreeze(@RequestParam("customerId") String customerId,
                               Map<String, Object> map) {

        try {
            customerService.unFreeze(customerId);
        } catch (Exception e) {
            map.put("msg", e.getMessage());
            map.put("url", "/diandiancar/admin/customer/list");
            return new ModelAndView("/common/error", map);
        }
        map.put("url", "/diandiancar/admin/customer/list");
        return new ModelAndView("/common/success", map);
    }
}
