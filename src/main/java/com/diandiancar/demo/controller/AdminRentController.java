package com.diandiancar.demo.controller;

import com.diandiancar.demo.dto.RentDTO;
import com.diandiancar.demo.enums.ResultEnum;
import com.diandiancar.demo.service.RentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.Map;

@Controller
@RequestMapping("/admin/rent")
@Slf4j
public class AdminRentController {

    @Autowired
    private RentService rentService;

    @GetMapping("/list")
    public ModelAndView list(@RequestParam(value = "page", defaultValue = "1")Integer page,
                             @RequestParam(value = "size",defaultValue = "5")Integer size,
                             Map<String,Object> map){
        PageRequest pageRequest = PageRequest.of(page-1,size);
        Page<RentDTO> rentDTOPage = rentService.findAll(pageRequest);
        map.put("rentDTOPage",rentDTOPage);
        map.put("currentPage",page);
        map.put("size",size);

        return new ModelAndView("/admin/rent/list");
    }

    @GetMapping("/cancel")
    public ModelAndView cancel(@RequestParam("rentId") String rentId,
                               Map<String, Object> map) {

        RentDTO rentDTO = null;
        try {
            rentDTO = rentService.findOne(rentId);
            rentService.cancel(rentDTO);
        } catch (Exception e) {
            log.error("【管理员取消租借单】发生异常，{}", e);
            map.put("msg", e.getMessage());
            map.put("url", "/diandiancar/admin/rent/list");
            return new ModelAndView("/common/error", map);
        }
        map.put("msg", ResultEnum.RENT_CANCEL_SUCCESS.getMessage());
        map.put("url", "/diandiancar/admin/rent/list");
        return new ModelAndView("/common/success", map);

    }

    @GetMapping("/finish")
    public ModelAndView finish(@RequestParam("rentId") String rentId,
                               Map<String, Object> map) {

        RentDTO rentDTO = null;
        try {
            rentDTO = rentService.findOne(rentId);
            rentService.finish(rentDTO);
        } catch (Exception e) {
            log.error("【管理员完结租借单】发生异常，{}", e);
            map.put("msg", e.getMessage());
            map.put("url", "/diandiancar/admin/rent/list");
            return new ModelAndView("/common/error", map);
        }
        map.put("msg", ResultEnum.RENT_FINISH_SUCCESS.getMessage());
        map.put("url", "/diandiancar/admin/rent/list");
        return new ModelAndView("/common/success", map);

    }
}
