package com.diandiancar.demo.controller;

import com.diandiancar.demo.VO.CarInfoVO;
import com.diandiancar.demo.VO.CarVO;
import com.diandiancar.demo.VO.ResultVO;
import com.diandiancar.demo.entity.CarCategory;
import com.diandiancar.demo.entity.CarInfo;
import com.diandiancar.demo.enums.ResultEnum;
import com.diandiancar.demo.exception.CarException;
import com.diandiancar.demo.service.CarCategoryService;
import com.diandiancar.demo.service.CarInfoService;
import com.diandiancar.demo.util.ResultVOUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/customer/carInfo")
@Slf4j
public class CustomerCarInfoController {

    @Autowired
    private CarInfoService carInfoService;

    @Autowired
    private CarCategoryService carCategoryService;

    @GetMapping("/list")
    @ResponseBody
    public ResultVO list(@RequestParam(value = "carName",required = false)String carName) {

        List<CarInfo> carInfoList = new ArrayList<>();
        if(StringUtils.isEmpty(carName)){
        //1.查找所有可以租借的车辆
            carInfoList = carInfoService.findRentableAll();

        }else {
//            System.out.println(carName+"carNamecarNamecarNamecarNamecarNamecarNamecarName");
            carInfoList = carInfoService.findByCarNameLike("%" + carName + "%");
        }
        //2.查找所有车辆类型
        List<Integer> categoryTypeList = carInfoList.stream()
                .map(e -> e.getCategoryType())
                .collect(Collectors.toList());
        List<CarCategory> carCategoryList = carCategoryService.findByCarCategoryTypeIn(categoryTypeList);

        //3.数据拼装
        List<CarVO> carVOList = new ArrayList<>();
        for (CarCategory carCategory : carCategoryList) {
            CarVO carVO = new CarVO();
            carVO.setCarCategoryName(carCategory.getCategoryName());
            carVO.setCarCategoryType(carCategory.getCategoryType());

            List<CarInfoVO> carInfoVOList = new ArrayList<>();
            for (CarInfo carInfo : carInfoList) {
                if (carInfo.getCategoryType().equals(carCategory.getCategoryType())) {
                    CarInfoVO carInfoVO = new CarInfoVO();
                    BeanUtils.copyProperties(carInfo, carInfoVO);
                    carInfoVOList.add(carInfoVO);
                }
            }
            carVO.setCarInfoVOList(carInfoVOList);
            carVOList.add(carVO);
        }
        return ResultVOUtil.success(carVOList);
    }

    //搜索
    @GetMapping("/search")
    public ModelAndView search(@RequestParam(value = "carName",required = false)String carName,
                               @RequestParam(value = "page", defaultValue = "1") Integer page,
                               @RequestParam(value = "size", defaultValue = "9") Integer size,
                               Map<String,Object> map) {

        System.out.println("search");
        //1.根据carName查找所有可以租借的车辆
        PageRequest pageRequest = PageRequest.of(page - 1, size);
        if (StringUtils.isEmpty(carName) || carName == null) {

            Page<CarInfo> carInfoPage = carInfoService.findAll(pageRequest);
            if (carInfoPage == null) {
                map.put("msg", "查找结果为空");
                map.put("url", "/diandiancar/customer/index");
                return new ModelAndView("/common/error", map);
            }
            map.put("currentPage", page);
            map.put("size", size);

        }
        Page<CarInfo> carInfoPage = carInfoService.findByCarNameLike("%" + carName + "%", pageRequest);

        //2.查找所有车辆类型
        List<Integer> categoryTypeList = carInfoPage.getContent().stream()
                .map(e -> e.getCategoryType())
                .collect(Collectors.toList());
        List<CarCategory> carCategoryList = carCategoryService.findByCarCategoryTypeIn(categoryTypeList);

        //3.数据拼装
        List<CarVO> carVOList = new ArrayList<>();
        for (CarCategory carCategory : carCategoryList) {
            CarVO carVO = new CarVO();
            carVO.setCarCategoryName(carCategory.getCategoryName());
            carVO.setCarCategoryType(carCategory.getCategoryType());

            List<CarInfoVO> carInfoVOList = new ArrayList<>();
            for (CarInfo carInfo : carInfoPage.getContent()) {
                if (carInfo.getCategoryType().equals(carCategory.getCategoryType())) {
                    CarInfoVO carInfoVO = new CarInfoVO();
                    BeanUtils.copyProperties(carInfo, carInfoVO);
                    carInfoVOList.add(carInfoVO);
                }
            }
            carVO.setCarInfoVOList(carInfoVOList);

            carVOList.add(carVO);
        }
        map.put("carVOList",carVOList);
        map.put("currentPage", page);
        map.put("size", size);
        return new ModelAndView("/customer/car-search",map);
    }


    //跳转到车辆列表（不能删）
    @GetMapping("/carList")
    public ModelAndView carList(@RequestParam(value = "carName",required = false) String carName,
                                Map<String,Object> map) {
        if(!StringUtils.isEmpty(carName)){
            //搜索的请求，模糊查找
            map.put("carName",carName);
            map.put("hasCarName","hasCarName");
            return new ModelAndView("/customer/car-list",map);

        }
        map.put("hasCarName","notCarName");
        return new ModelAndView("/customer/car-list",map);
    }

    //获取单个车辆信息
    @GetMapping("/detail")
    public ModelAndView detail(@RequestParam(value = "carId") String carId,
                               Map<String,Object> map) {
        if (carId == null || StringUtils.isEmpty(carId)) {
            throw new CarException(ResultEnum.PARAM_ERROR);
        }
        CarInfo carInfo = carInfoService.findById(carId);

        map.put("carInfo",carInfo);

        return new ModelAndView("/customer/car-detail",map);
    }


    @RequestMapping("/to_list")
    public ModelAndView test() {
        System.out.println("to_list");
        return new ModelAndView("/customer/car-list");
    }

}
