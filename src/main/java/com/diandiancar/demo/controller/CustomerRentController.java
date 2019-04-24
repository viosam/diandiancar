package com.diandiancar.demo.controller;

import com.diandiancar.demo.VO.ResultVO;
import com.diandiancar.demo.converter.Rent2RentDTOConverter;
import com.diandiancar.demo.dto.BookDTO;
import com.diandiancar.demo.dto.RentDTO;
import com.diandiancar.demo.entity.CarInfo;
import com.diandiancar.demo.entity.Customer;
import com.diandiancar.demo.entity.Province;
import com.diandiancar.demo.entity.Rent;
import com.diandiancar.demo.enums.ResultEnum;
import com.diandiancar.demo.exception.CarException;
import com.diandiancar.demo.form.RentForm;
import com.diandiancar.demo.repository.ProvinceRepository;
import com.diandiancar.demo.repository.RentRepository;
import com.diandiancar.demo.service.BookService;
import com.diandiancar.demo.service.CarInfoService;
import com.diandiancar.demo.service.RentService;
import com.diandiancar.demo.util.CalculationDate;
import com.diandiancar.demo.util.ResultVOUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/customer/rent")
@Slf4j
public class CustomerRentController {

    @Autowired
    private RentService rentService;

    @Autowired
    private CarInfoService carInfoService;

    @Autowired
    private BookService bookService;

    @Autowired
    private RentRepository rentRepository;

    @Autowired
    private ProvinceRepository provinceRepository;

    @GetMapping("/list")
    @ResponseBody
    public ResultVO list(HttpServletRequest request) {
        Customer customer = (Customer) request.getSession().getAttribute("customer");
        String customerId = customer.getId();
        List<RentDTO> rentDTOList = rentService.findByCustomerId(customerId);
        if (rentDTOList.size() <= 0) {
            throw new CarException(ResultEnum.RENT_NOT_FOUND);
        }

        return ResultVOUtil.success(rentDTOList);
    }

    @GetMapping("/toList")
    public ModelAndView toList(Map<String, Object> map, HttpServletRequest request) {
        System.out.println("toList");

        Customer customer = (Customer) request.getSession().getAttribute("customer");
        if (customer == null) {
//            map.put("msg", "请登录");
//            map.put("url", "/diandiancar/customer/login.jsp");
            return new ModelAndView("/customer/login", map);
        }
        String customerId = customer.getId();

        List<RentDTO> rentDTOList = rentService.findByCustomerId(customerId);
        if (rentDTOList.size() <= 0) {
            throw new CarException(ResultEnum.RENT_NOT_FOUND);
        }
        map.put("rentDTOList", rentDTOList);
        return new ModelAndView("/customer/rent-list", map);
    }

    //租借单详情
    @GetMapping("/detail")
    public ModelAndView detail(@RequestParam("rentId") String rentId,
                               HttpServletRequest request,
                               Map<String, Object> map) {

        Customer customer = (Customer) request.getSession().getAttribute("customer");
        if (customer == null) {
            return new ModelAndView("/customer/login", map);
        }
        String customerId = customer.getId();
        RentDTO rentDTO = null;
        try {
            rentDTO = rentService.findByIdAndCustomerId(rentId,customerId);
        } catch (Exception e) {
            log.error("【预约详情】发生异常，{}", e);
            map.put("msg", e.getMessage());
            map.put("url", "/diandiancar/customer/rent/toList");
            return new ModelAndView("/common/error", map);
        }
        map.put("rentDTO", rentDTO);
        return new ModelAndView("/customer/rent-detail", map);

    }

    //租借列表点击 去支付押金后 跳转到支付押金页面，确认信息并支付）
    @GetMapping("/deposit_index")
    public ModelAndView depositIndex(@RequestParam(value = "rentId", required = true) String rentId,
                                     HttpServletRequest request,
                                     Map<String, Object> map) {
        if (StringUtils.isEmpty(rentId)) {
            map.put("msg", "请求失败");
            map.put("url", "/diandiancar/customer/rent/toList");
            return new ModelAndView("/common/error", map);
        }
        Customer customer = (Customer) request.getSession().getAttribute("customer");
        if (customer == null) {
            return new ModelAndView("/customer/login", map);
        }
        //rentId，为了给页面做回显
        RentDTO rentDTO = rentService.findOne(rentId);
//        BookDTO bookDTO = bookService.findOne(rentDTO.getBookId());
        CarInfo carInfo = carInfoService.findById(rentDTO.getCarId());

        if (rentDTO == null) {
            map.put("msg", ResultEnum.RENT_NOT_EXIST);
            map.put("url", "/diandiancar/customer/rent/toList");
            return new ModelAndView("/common/error", map);
        }
        BookDTO bookDTO = bookService.findOne(rentDTO.getBookId());
        map.put("bookDTO",bookDTO);
        map.put("rentDTO", rentDTO);
        map.put("carInfo", carInfo);
        return new ModelAndView("/customer/deposit-index", map);
    }

    //租借列表点击 支付租金后 跳转到支付押金页面，确认信息并支付）
    @GetMapping("/rent_index")
    public ModelAndView rentIndex(@RequestParam(value = "rentId", required = true) String rentId,
                                  HttpServletRequest request,
                                  Map<String, Object> map) {
        if (StringUtils.isEmpty(rentId)) {
            map.put("msg", "请求失败");
            map.put("url", "/diandiancar/customer/rent/toList");
            return new ModelAndView("/common/error", map);
        }

        //获取省份
        List<Province> provinceList = provinceRepository.findAll();
        map.put("provinceList",provinceList);

        Customer customer = (Customer) request.getSession().getAttribute("customer");
        if (customer == null) {
            return new ModelAndView("/customer/login", map);
        }
        //rentId，为了给页面做回显
        RentDTO rentDTO = rentService.findOne(rentId);
//        BookDTO bookDTO = bookService.findOne(rentDTO.getBookId());
        CarInfo carInfo = carInfoService.findById(rentDTO.getCarId());

        if (rentDTO == null) {
            map.put("msg", ResultEnum.RENT_NOT_EXIST);
            map.put("url", "/diandiancar/customer/rent/toList");
            return new ModelAndView("/common/error", map);
        }
        //向用户展示预计支付的租金
        rentDTO.setEndDate(new Date());
        try {
            int sumDate = new Long(CalculationDate.calculationDate(rentDTO.getBeginDate()
                    , rentDTO.getEndDate())).intValue();

            rentDTO.setRentSumDate(sumDate);
            //支付金额减去订金
            rentDTO.setPaymentAmountRent(carInfo.getRentPrice()
                    .multiply(new BigDecimal(rentDTO.getRentSumDate())));
        } catch (ParseException e) {
            e.printStackTrace();
        }

        map.put("rentDTO", rentDTO);
        map.put("carInfo", carInfo);
        return new ModelAndView("/customer/rent-index", map);
    }


    /**
     * @param id  租借单号
     * @param map
     * @return
     */
    @RequestMapping("/to_deposit_pay")
    public ModelAndView toDepositPay(@RequestParam(value = "id", required = true) String id,
                                     HttpServletRequest request,
                                     Map<String, Object> map) {
        if (StringUtils.isEmpty(id)) {
            map.put("msg", ResultEnum.PARAM_ERROR);
            map.put("url", "/diandiancar/customer/rent/toList");
            return new ModelAndView("/common/error", map);
        }
        Customer customer = (Customer) request.getSession().getAttribute("customer");
        if (customer == null) {
            return new ModelAndView("/customer/login", map);
        }
        RentDTO rentDTO = rentService.findOne(id);
        if (rentDTO == null) {
            map.put("msg", ResultEnum.RENT_NOT_EXIST);
            map.put("url", "/diandiancar/customer/rent/toList");
            return new ModelAndView("/common/error", map);
        }

        //跳转到统一支付页面：pay
        map.put("money", rentDTO.getPaymentAmountDeposit());
        map.put("orderId", rentDTO.getId());
        //设置返回按钮forBackUrl的url
        map.put("forBackUrl", "/diandiancar/customer/rent/toList");
        //设置确认按钮toUrl的url,预约支付完成后返回预约列表
        map.put("toUrl", "/diandiancar/customer/rent/pay_deposit?id="+rentDTO.getId());
        return new ModelAndView("/common/pay/pay", map);

    }

    /**
     * @param id  租借单号
     * @param map
     * @return
     */
    @RequestMapping("/to_rent_pay")
    public ModelAndView toRentPay(@RequestParam(value = "id", required = true) String id,
                                  @RequestParam(value = "toShop", required = true) String toShop,
                                  HttpServletRequest request,
                                  Map<String, Object> map) {
        if (StringUtils.isEmpty(id)) {
            map.put("msg", ResultEnum.PARAM_ERROR);
            map.put("url", "/diandiancar/customer/rent/toList");
            return new ModelAndView("/common/error", map);
        }
        Customer customer = (Customer) request.getSession().getAttribute("customer");
        if (customer == null) {
            return new ModelAndView("/customer/login", map);
        }
        RentDTO rentDTO = rentService.findOne(id);
        if (rentDTO == null) {
            map.put("msg", ResultEnum.RENT_NOT_EXIST);
            map.put("url", "/diandiancar/customer/rent/toList");
            return new ModelAndView("/common/error", map);
        }
        CarInfo carInfo = carInfoService.findById(rentDTO.getCarId());
        //租借结束日根据实际归还时间
        rentDTO.setEndDate(new Date());
        int sumDate = 0;
        try {
            sumDate = new Long(CalculationDate.calculationDate(rentDTO.getBeginDate()
                    , rentDTO.getEndDate())).intValue();
        } catch (ParseException e) {
            map.put("msg", "归还日期有误！");
            map.put("url", "/diandiancar/customer/rent/toList");
            return new ModelAndView("/common/error", map);

        }
        rentDTO.setRentSumDate(sumDate);
        //支付金额减去订金
        rentDTO.setPaymentAmountRent(carInfo.getRentPrice()
                .multiply(new BigDecimal(rentDTO.getRentSumDate())));

        //跳转到统一支付页面：pay
        map.put("money", rentDTO.getPaymentAmountRent());
        map.put("orderId", rentDTO.getId());
        //设置返回按钮forBackUrl的url
        map.put("forBackUrl", "/diandiancar/customer/book/toList");
        //设置确认按钮toUrl的url,预约支付完成后返回预约列表
        map.put("toUrl", "/diandiancar/customer/rent/pay_rent?id="+rentDTO.getId()+"&toShop="+toShop);
        return new ModelAndView("/common/pay/pay", map);

    }

    //deposit-index页面调用此支付押金功能，更改rent状态
    @GetMapping("/pay_deposit")
    public ModelAndView payDeposit(@RequestParam(value = "id", required = true) String id,
                                   HttpServletRequest request,
                                   Map<String, Object> map) throws ParseException {
        System.out.println("pay");
        if (StringUtils.isEmpty(id)) {
            map.put("msg", ResultEnum.PARAM_ERROR);
            map.put("url", "/diandiancar/customer/rent/toList");
            return new ModelAndView("/common/error", map);
        }
        Customer customer = (Customer) request.getSession().getAttribute("customer");
        if (customer == null) {
            return new ModelAndView("/customer/login", map);
        }
        String customerId = customer.getId();

        RentDTO resultRent = rentService.findOne(id);
        if (resultRent == null) {
            map.put("msg", "租借单号不存在！");
            map.put("url", "/diandiancar/customer/rent/toList");
            return new ModelAndView("/common/error", map);
        }

        try {
            //更新租借信息
            rentService.paidDeposit(resultRent);
        } catch (Exception e) {
            map.put("msg", "支付失败");
            map.put("url", "/diandiancar/customer/rent/toList");
            return new ModelAndView("/common/error", map);
        }

        map.put("resultRent", resultRent);
        map.put("url", "/diandiancar/customer/rent/toList");
        return new ModelAndView("/common/pay/success", map);
    }


    //TODO 还车页面(租借列表页面点击查看租借详情（未做）)
    //还车页面(租借列表页面点击查看租借详情)调用此支付租金功能
    @GetMapping("/pay_rent")
    public ModelAndView payRent(@RequestParam(value = "id", required = true) String id,
                                @RequestParam(value = "toShop", required = true) String toShop,
                                HttpServletRequest request,
                                Map<String, Object> map) throws ParseException {
        System.out.println("pay");
        if (StringUtils.isEmpty(id)) {
            map.put("msg", ResultEnum.PARAM_ERROR);
            map.put("url", "/diandiancar/customer/rent/toList");
            return new ModelAndView("/common/error", map);
        }
        Customer customer = (Customer) request.getSession().getAttribute("customer");
        if (customer == null) {
            return new ModelAndView("/customer/login", map);
        }
        String customerId = customer.getId();


        Rent rent = rentRepository.findById(id).get();
        rent.setToShop(toShop);
        rentRepository.save(rent);
        RentDTO resultRent = Rent2RentDTOConverter.convert(rent);
        if (resultRent == null) {
            map.put("msg", "租借单号不存在！");
            map.put("url", "/diandiancar/customer/rent/toList");
            return new ModelAndView("/common/error", map);
        }
        try {
            //更新租借信息
            rentService.paidRent(resultRent);
        } catch (Exception e) {
            map.put("msg", "支付失败");
            map.put("url", "/diandiancar/customer/rent/toList");
            return new ModelAndView("/common/error", map);
        }

        map.put("resultRent", resultRent);
        map.put("url", "/diandiancar/customer/rent/toList");
        return new ModelAndView("/common/pay/success", map);
    }

    //交押金之前才能取消
    @GetMapping("/cancel")
    public ModelAndView cancel(@RequestParam(value = "rentId", required = false) String rentId,
                               HttpServletRequest request,
                               Map<String, Object> map) throws Exception {
        System.out.println("cancel");

        Customer customer = (Customer) request.getSession().getAttribute("customer");
        if (customer == null) {
            return new ModelAndView("/customer/login", map);
        }
        String customerId = customer.getId();


        RentDTO resultRent = rentService.findOne(rentId);

        try {
            rentService.cancel(resultRent);
        } catch (Exception e) {
            map.put("msg", "取消失败");
            map.put("url", "/diandiancar/customer/rent/toList");
            return new ModelAndView("/common/error", map);
        }

        map.put("resultRent", resultRent);
        map.put("url", "/diandiancar/customer/rent/toList");
        return new ModelAndView("/common/success", map);
    }

}
