package com.diandiancar.demo.controller;

import com.diandiancar.demo.dto.BookDTO;
import com.diandiancar.demo.dto.RentDTO;
import com.diandiancar.demo.dto.RepairDTO;
import com.diandiancar.demo.entity.CarInfo;
import com.diandiancar.demo.entity.Customer;
import com.diandiancar.demo.enums.ResultEnum;
import com.diandiancar.demo.form.RentForm;
import com.diandiancar.demo.form.RepairForm;
import com.diandiancar.demo.service.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/customer/repair")
@Slf4j
public class CustomerRepairController {

    @Autowired
    private RepairService repairService;

    @Autowired
    private BookService bookService;

    @Autowired
    private CarInfoService carInfoService;

    @Autowired
    private RentService rentService;

    @Autowired
    private FileUpload fileUpload;

    /*@PostMapping("/create")
    public ModelAndView create(@Valid RepairForm repairForm,
                               BindingResult bindingResult,
                               HttpServletRequest request,
                               Map<String, Object> map) throws Exception {

        if (bindingResult.hasErrors()) {
            map.put("msg", bindingResult.getFieldError().getDefaultMessage());
            map.put("url", "/diandiancar/customer/repair/toList");
            return new ModelAndView("/common/error", map);
        }

        Customer customer = (Customer) request.getSession().getAttribute("customer");
        if (customer == null) {
            map.put("msg", "请登录");
            map.put("url", "/diandiancar/customer/login.jsp");
            return new ModelAndView("/common/error", map);
        }

        String customerId = customer.getId();
        repairForm.setCustomerId(customerId);

        RepairDTO repairDTO = new RepairDTO();

        //上传图片,并获取文件路径
        List<String> resultUrl = fileUpload.upload(request,"iconsFile");

        try {
            BeanUtils.copyProperties(repairForm, repairDTO);
            repairDTO.setIcons(resultUrl.get(0));
            repairService.create(repairDTO);
        } catch (Exception e) {
            map.put("msg", e.getMessage());
            map.put("url", "/diandiancar/customer/repair/toList");
            return new ModelAndView("/common/error", map);
        }
        map.put("url", "/diandiancar/customer/repair/toList");
            return new ModelAndView("/common/success", map);
    }

    //跳转到报修页面
    @GetMapping("/index")
    public ModelAndView index(@RequestParam(value = "rentId", required = true) String rentId,
                              @RequestParam(value = "bookId", required = true) String bookId,
                              Map<String, Object> map) {
        if (StringUtils.isEmpty(rentId)||StringUtils.isEmpty(bookId)) {
            map.put("msg", "请求失败");
            map.put("url", "/diandiancar/customer/rent/toList");
            return new ModelAndView("/common/error", map);
        }

        //下个页面需要car的信息，但是rent没有设计car相关字段，所以只能从bookid查
        BookDTO resultBookDTO = bookService.findOne(bookId);
        CarInfo resultCarInfo = carInfoService.findById(resultBookDTO.getCarID());

        RentDTO rentDTO = rentService.findOne(rentId);

        if (resultBookDTO == null||resultCarInfo==null||rentDTO==null) {
            map.put("msg", ResultEnum.RENT_NOT_EXIST);
            map.put("url", "/diandiancar/customer/rent/toList");
            return new ModelAndView("/common/error", map);
        }
        map.put("carInfo", resultCarInfo);
        map.put("rentDTO", rentDTO);
        return new ModelAndView("/customer/repair-index", map);
    }


    @GetMapping("/toList")
    public ModelAndView toList(Map<String, Object> map, HttpServletRequest request) {

        Customer customer = (Customer) request.getSession().getAttribute("customer");
        String customerId = customer.getId();
        List<RepairDTO> repairDTOList = repairService.findByCustomerId(customerId);
        if (repairDTOList.size() <= 0) {
            map.put("msg", "暂无维修记录");
            map.put("url", "/diandiancar/customer/carInfo/carList");
            return new ModelAndView("/common/error", map);
        }
        map.put("repairDTOList", repairDTOList);
        return new ModelAndView("/customer/repair-list", map);
    }*/
}
