package com.diandiancar.demo.controller;

import com.diandiancar.demo.VO.ResultVO;
import com.diandiancar.demo.dto.BookDTO;
import com.diandiancar.demo.entity.CarInfo;
import com.diandiancar.demo.entity.Customer;
import com.diandiancar.demo.entity.Province;
import com.diandiancar.demo.enums.ResultEnum;
import com.diandiancar.demo.exception.CarException;
import com.diandiancar.demo.form.BookForm;
import com.diandiancar.demo.repository.ProvinceRepository;
import com.diandiancar.demo.service.BookService;
import com.diandiancar.demo.service.CarInfoService;
import com.diandiancar.demo.service.CreditService;
import com.diandiancar.demo.util.ResultVOUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/customer/book")
@Slf4j
public class CustomerBookController {

    @Autowired
    private BookService bookService;

    @Autowired
    private CarInfoService carInfoService;

    @Autowired
    private CreditService creditService;

    @Autowired
    ProvinceRepository provinceRepository;

    @GetMapping("/list")
    @ResponseBody
    public ResultVO list(HttpServletRequest request) {

        Customer customer = (Customer) request.getSession().getAttribute("customer");
        /*if (customer==null) {
            map.put("msg", "请登录！");
            map.put("url", "/diandiancar/customer/toLogin");
            return new ModelAndView("/common/error", map);
        }*/
        String customerId = customer.getId();

        List<BookDTO> bookDTOList = bookService.findByCustomerId(customerId);

        if (bookDTOList.size() <= 0) {
            throw new CarException(ResultEnum.RENT_NOT_FOUND);
        }
        return ResultVOUtil.success(bookDTOList);
    }

    @GetMapping("/toList")
    public ModelAndView toList(Map<String, Object> map, HttpServletRequest request) {
        System.out.println("toList");

        Customer customer = (Customer) request.getSession().getAttribute("customer");
        if (customer==null) {
            return new ModelAndView("/customer/login", map);
        }
        String customerId = customer.getId();
        List<BookDTO> bookDTOList = bookService.findByCustomerId(customerId);
        if (bookDTOList.size() <= 0) {
            throw new CarException(ResultEnum.BOOK_NOT_FOUND);
        }
        map.put("bookDTOList", bookDTOList);
        return new ModelAndView("/customer/book-list", map);
    }

    //转发到book-index页面（点击我要预约按钮后跳转，为了做数据回显）
    @GetMapping("/index")
    public ModelAndView index(@RequestParam(value = "carId", required = true) String carId,
                              Map<String, Object> map) {
        if (carId == "" || StringUtils.isEmpty(carId)) {
            map.put("msg", "请求失败");
            map.put("url", "/diandiancar/customer/carInfo/detail?carId=" + carId);
            return new ModelAndView("/common/error", map);
        }
        //根据carId查找，为了给页面做回显
        CarInfo carInfo = carInfoService.findById(carId);
        if (carInfo == null) {
            map.put("msg", ResultEnum.CAR_NOT_EXIST);
            map.put("url", "/diandiancar/customer/carInfo/carList");
            return new ModelAndView("/common/error", map);

        }
        List<Province> provinceList = provinceRepository.findAll();
        map.put("provinceList",provinceList);
        map.put("carInfo", carInfo);
        return new ModelAndView("/customer/book-index", map);
    }

    //预约单详情
    @GetMapping("/detail")
    public ModelAndView detail(@RequestParam("bookId") String bookId,
                               HttpServletRequest request,
                               Map<String, Object> map) {

        Customer customer = (Customer) request.getSession().getAttribute("customer");
        if (customer==null) {
            return new ModelAndView("/customer/login", map);
        }
        String customerId = customer.getId();
        BookDTO bookDTO = null;
        try {
            bookDTO = bookService.findByIdAndCustomerID(bookId,customerId);
        } catch (Exception e) {
            log.error("【预约详情】发生异常，{}", e);
            map.put("msg", e.getMessage());
            map.put("url", "/diandiancar/customer/book/toList");
            return new ModelAndView("/common/error", map);
        }
        map.put("bookDTO", bookDTO);

        return new ModelAndView("/customer/book-detail", map);

    }

    @PostMapping("/create")
    public ModelAndView create(@Valid BookForm bookForm,
                               BindingResult bindingResult,
                               HttpServletRequest request,
                               Map<String, Object> map) throws Exception {

        if (bindingResult.hasErrors()) {
            map.put("msg", bindingResult.getFieldError().getDefaultMessage());
            map.put("url", "/diandiancar/customer/carInfo/carList");
            return new ModelAndView("/common/error", map);
        }
        System.out.println(bookForm.getFromShop());

        Customer customer = (Customer) request.getSession().getAttribute("customer");
        if (customer == null) {
            return new ModelAndView("/customer/login", map);
        }
        CarInfo carInfo = carInfoService.findById(bookForm.getCarID());
        if (carInfo == null) {
            map.put("msg", "该车不存在");
            map.put("url", "/diandiancar/customer/carInfo/carList");
            return new ModelAndView("/common/error", map);
        }
        String customerId = customer.getId();

        bookForm.setCustomerID(customerId);
        //计算定金
        BigDecimal earnestMoney = creditService.getBookMoneyByCreditLevel(customer.getCreditLevel());
        bookForm.setEarnestMoney(earnestMoney);

        BookDTO bookDTO = new BookDTO();

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        bookDTO.setBookBeginDate(sdf.parse(bookForm.getBookBeginDate()));
        bookDTO.setBookEndDate(sdf.parse(bookForm.getBookEndDate()));
        BookDTO resultBookDTO = new BookDTO();
        try {
            BeanUtils.copyProperties(bookForm, bookDTO);
            bookDTO.setCustomerID(customerId);
            resultBookDTO = bookService.create(bookDTO);
            map.put("carInfo", carInfo);
            map.put("resultBookDTO", resultBookDTO);
        } catch (Exception e) {
            map.put("msg", e.getMessage());
            map.put("url", "/diandiancar/customer/carInfo/detail?carId=" + bookForm.getCarID());
            return new ModelAndView("/common/error", map);
        }
        //跳转到统一支付页面：pay
        map.put("money", bookDTO.getEarnestMoney());
        map.put("orderId", resultBookDTO.getId());
        //设置返回按钮forBackUrl的url
        map.put("forBackUrl", "/diandiancar/customer/book/index?carId=" + bookForm.getCarID());
        //设置确认按钮toUrl的url
        map.put("toUrl", "/diandiancar/customer/book/pay?id=" + resultBookDTO.getId());
         return new ModelAndView("/common/pay/pay", map);
    }


    @RequestMapping("/toPay")
    public ModelAndView toPay(@RequestParam(value = "id", required = true) String id,
                              HttpServletRequest request,
                              Map<String, Object> map) {
        if (StringUtils.isEmpty(id)) {
            map.put("msg", ResultEnum.PARAM_ERROR);
            map.put("url", "/diandiancar/customer/book/toList");
            return new ModelAndView("/common/error", map);
        }
        Customer customer = (Customer) request.getSession().getAttribute("customer");
        if (customer == null) {
            return new ModelAndView("/customer/login", map);
        }
        BookDTO bookDTO = bookService.findOne(id);
        if (bookDTO == null) {
            map.put("msg", ResultEnum.BOOK_NOT_EXIST);
            map.put("url", "/diandiancar/customer/book/toList");
            return new ModelAndView("/common/error", map);
        }

        //跳转到统一支付页面：pay
        map.put("money", bookDTO.getEarnestMoney());
        map.put("orderId", bookDTO.getId());
        //设置返回按钮forBackUrl的url
        map.put("forBackUrl", "/diandiancar/customer/book/toList");
        //设置确认按钮toUrl的url,预约支付完成后返回预约列表
        map.put("toUrl", "/diandiancar/customer/book/pay?id="+bookDTO.getId());
        return new ModelAndView("/common/pay/pay", map);

    }

    //先完结book然后创建rent,若创建成功,更新支付状态、更新车辆状态，则返回支付成功页面
    @GetMapping("/pay")
    public ModelAndView pay(@RequestParam(value = "id", required = true) String id,
                            HttpServletRequest request,
                            Map<String, Object> map) {
        System.out.println("pay");
        if (StringUtils.isEmpty(id)) {
            map.put("msg", ResultEnum.PARAM_ERROR);
            map.put("url", "/diandiancar/customer/book/toList");
            return new ModelAndView("/common/error", map);
        }
        Customer customer = (Customer) request.getSession().getAttribute("customer");
        if (customer == null) {
            return new ModelAndView("/customer/login", map);
        }
        String customerId = customer.getId();

        BookDTO resultBook = bookService.findByIdAndCustomerID(id, customerId);
        if (resultBook == null) {
            map.put("msg", "预约单号不存在");
            map.put("url", "/diandiancar/customer/book/toList");
            return new ModelAndView("/common/error", map);
        }

        BookDTO resultBookDTO = bookService.paid(resultBook);
        if (resultBookDTO == null) {
            map.put("msg", "支付失败");
            map.put("url", "/diandiancar/customer/book/toList");
            return new ModelAndView("/common/error", map);
        }

        map.put("resultBookDTO", resultBookDTO);
        //支付成功，跳转到租借页面
        map.put("url", "/diandiancar/customer/rent/toList");
        return new ModelAndView("/common/pay/success", map);
    }

 /*   @GetMapping("/toRent")
    public ModelAndView toRent(@RequestParam(value = "bookId", required = true) String bookId,
                               Map<String, Object> map, HttpServletRequest request) {
        System.out.println("toRent");

        Customer customer = (Customer) request.getSession().getAttribute("customer");
        String customerId = customer.getId();
        BookDTO resultBookDTO = bookService.findOne(bookId);
        if (resultBookDTO == null) {

            map.put("msg", "预约单号错误");
            map.put("url", "/diandiancar/customer/book/toList");
            return new ModelAndView("/common/error", map);
        }
        map.put("resultBookDTO", resultBookDTO);
        return new ModelAndView("/customer/rent-index", map);
    }
*/

    //更改book状态
    @GetMapping("/cancel")
    public ModelAndView cancel(@RequestParam(value = "bookId", required = false) String bookId,
                               HttpServletRequest request,
                               Map<String, Object> map) throws Exception {
        System.out.println("cancel");

        Customer customer = (Customer) request.getSession().getAttribute("customer");
        if (customer == null) {
            return new ModelAndView("/customer/login", map);
        }
        String customerId = customer.getId();



        BookDTO resultBook = bookService.findOne(bookId);

        try {
            bookService.cancel(resultBook);
        } catch (Exception e) {
            map.put("msg", "取消失败");
            map.put("url", "/diandiancar/customer/book/toList");
            return new ModelAndView("/common/error", map);
        }

        map.put("resultRent", resultBook);
        map.put("url", "/diandiancar/customer/book/toList");
        return new ModelAndView("/common/success", map);
    }

}
