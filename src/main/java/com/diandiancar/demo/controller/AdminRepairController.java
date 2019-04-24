package com.diandiancar.demo.controller;

import com.diandiancar.demo.dto.RepairDTO;
import com.diandiancar.demo.enums.ResultEnum;
import com.diandiancar.demo.service.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/admin/repair")
@Slf4j
public class AdminRepairController {

    @Autowired
    private RepairService repairService;

    @GetMapping("/toList")
    public ModelAndView toList(@RequestParam(value = "page", defaultValue = "1") Integer page,
                               @RequestParam(value = "size", defaultValue = "5") Integer size,
                               Map<String, Object> map) {

        PageRequest pageRequest = PageRequest.of(page - 1, size);

        Page<RepairDTO> repairDTOPage = repairService.findAll(pageRequest);
        if (repairDTOPage.getContent().size() <= 0) {
            map.put("msg", "暂无维修信息");
            map.put("url", "/diandiancar/admin/index");
            return new ModelAndView("/common/error",map);
        }
        map.put("repairDTOPage", repairDTOPage);
        map.put("currentPage", page);
        map.put("size", size);
        return new ModelAndView("/admin/repair/list",map);
    }

    //通过审核
    @GetMapping("/approved_or_fail")
    public ModelAndView approvedOrFail(@RequestParam("repairId") String repairId,
                               @RequestParam("tag") String tag,
                               Map<String, Object> map) {

        RepairDTO repairDTO = repairService.approved(repairId,tag);

        if(repairDTO==null){
            map.put("msg", "车辆报修审核失败！");
            map.put("url", "/diandiancar/admin/repair/toList");
            return new ModelAndView("/common/error", map);
        }

        map.put("url", "/diandiancar/admin/repair/toList");
        return new ModelAndView("/common/success", map);

    }

    //报修
    @GetMapping("create")
    public ModelAndView approvedOrFail(@RequestParam("carId") String carId,
                                       Map<String, Object> map) {

        RepairDTO repairDTO = repairService.create(carId);

        if(repairDTO==null){
            map.put("msg", "车辆报修失败！");
            map.put("url", "/diandiancar/admin/carInfo/list");
            return new ModelAndView("/common/error", map);
        }

        map.put("url", "/diandiancar/admin/carInfo/list");
        return new ModelAndView("/common/success", map);
    }

}
