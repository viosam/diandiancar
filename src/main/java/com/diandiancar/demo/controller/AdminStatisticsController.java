package com.diandiancar.demo.controller;


import com.diandiancar.demo.VO.StatisticsCarInOutLib;
import com.diandiancar.demo.dto.RentDTO;
import com.diandiancar.demo.dto.RepairDTO;
import com.diandiancar.demo.entity.CarInfo;
import com.diandiancar.demo.enums.CarStatusEnum;
import com.diandiancar.demo.repository.CarInfoRepository;
import com.diandiancar.demo.service.CarInfoService;
import com.diandiancar.demo.service.RentService;
import com.diandiancar.demo.service.RepairService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.text.ParseException;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/admin/statistics")
public class AdminStatisticsController {

    @Autowired
    private RentService rentService;

    @Autowired
    private RepairService repairService;

    @Autowired
    private CarInfoService carInfoService;

    @Autowired
    private CarInfoRepository carInfoRepository;

    @RequestMapping("/index")
    public ModelAndView statistics(Map<String, Object> map) {

        return new ModelAndView("/admin/statistics/in-out-library", map);
    }

    /**
     * 车辆出入库统计
     *
     * @param map
     * @param status
     */
    @RequestMapping("/in_out_library")
    public ModelAndView inOutLibrary(@RequestParam(value = "page", defaultValue = "1") Integer page,
                                     @RequestParam(value = "size", defaultValue = "5") Integer size,
                                     @RequestParam(value = "status", required = false) Integer status,
                                     Map<String, Object> map) {

        PageRequest pageRequest = PageRequest.of(page - 1, size);
        Page<RentDTO> rentDTOPage = null;
        StatisticsCarInOutLib statisticsCarInOutLib = new StatisticsCarInOutLib();
        //其他状态的车辆：
        Integer inStocks = 0;
        //若车辆状态为空则搜索所有
        if (status == null) {
            rentDTOPage = rentService.findAll(pageRequest);
            //获取已归还/未归还的车辆数
            List<CarInfo> carInfoList = carInfoRepository.findAll();
            Integer returned = 0;
            Integer notReturend = 0;

            for (CarInfo carInfo:carInfoList){
                if(carInfo.getStatus()==CarStatusEnum.NO_RETURNED.getCode()
                        ||carInfo.getStatus()==CarStatusEnum.RENTED.getCode()){
                    ++notReturend;
                }
                if(carInfo.getStatus()==CarStatusEnum.RETURNED.getCode()){
                    ++returned;
                }
            }
            inStocks =carInfoList.size()-returned-notReturend;
            statisticsCarInOutLib.setNotReturend(notReturend);
            statisticsCarInOutLib.setReturned(returned);
            statisticsCarInOutLib.setRentDTOPage(rentDTOPage);

        } else if (status == CarStatusEnum.RETURNED.getCode() || status == CarStatusEnum.NO_RETURNED.getCode()) {
            map.put("status",status);
            statisticsCarInOutLib = rentService.findByCarStatus(status, pageRequest);
        }
        if (statisticsCarInOutLib == null) {
            map.put("msg", "暂无记录！");
            map.put("url", "/diandiancar/admin/index");
            return new ModelAndView("/common/error", map);
        }
        map.put("inStocks",inStocks);
        map.put("currentPage",page);
        map.put("size",size);
        map.put("statisticsCarInOutLib", statisticsCarInOutLib);

        return new ModelAndView("/admin/statistics/in-out-library", map);
    }

    /**
     * 车辆维修统计
     *
     * @param page
     * @param size
     * @param beginDate
     * @param endDate
     * @param map
     * @return
     */
    @RequestMapping("/repair")
    public ModelAndView repair(@RequestParam(value = "page", defaultValue = "1") Integer page,
                               @RequestParam(value = "size", defaultValue = "5") Integer size,
                               @RequestParam(value = "beginDate", required = false) String beginDate,
                               @RequestParam(value = "endDate", required = false) String endDate,
                               Map<String, Object> map) {

        //前台校验时间不为空、开始时间不能大于结束时间，时间不能大于今天
        PageRequest pageRequest = PageRequest.of(page - 1, size);
        Page<RepairDTO> repairDTOPage = null;
        //若起始结束时间为空则搜索所有
        if (StringUtils.isEmpty(beginDate) && StringUtils.isEmpty(endDate)) {
            repairDTOPage = repairService.findAll(pageRequest);
        } else if (!StringUtils.isEmpty(beginDate) || !StringUtils.isEmpty(endDate)) {
            try {
                repairDTOPage = repairService.findByCreateTimeBetween(beginDate, endDate, pageRequest);
            } catch (ParseException e) {
                map.put("msg", "查询错误");
                map.put("url", "/diandiancar/admin/index");
                return new ModelAndView("/common/error", map);
            }
        }
        if (repairDTOPage == null) {
            map.put("msg", "暂无记录！");
            map.put("url", "/diandiancar/admin/index");
            return new ModelAndView("/common/error", map);
        }
        map.put("repairDTOPage", repairDTOPage);
        return new ModelAndView("/admin/statistics/repair", map);
    }

    /**
     * 车辆租借统计
     * @param page
     * @param size
     * @param month 月份
     * @param map
     * @return
     */
    @RequestMapping("/car_rent")
    public ModelAndView carRent(@RequestParam(value = "page", defaultValue = "1") Integer page,
                                     @RequestParam(value = "size", defaultValue = "5") Integer size,
                                     @RequestParam(value = "month", required = false) Integer month,
                                     Map<String, Object> map) {

        PageRequest pageRequest = PageRequest.of(page - 1, size);
        Page<RentDTO> rentDTOPage = null;
        //若月份为空则搜索所有
        if (month == null) {
            rentDTOPage = rentService.findAll(pageRequest);
        } else{
            rentDTOPage = rentService.findByMonth(month, pageRequest);
        }
        if (rentDTOPage == null) {
            map.put("msg", "暂无记录！");
            map.put("url", "/diandiancar/admin/index");
            return new ModelAndView("/common/error", map);
        }
        map.put("rentDTOPage", rentDTOPage);

        return new ModelAndView("/admin/statistics/car-rent", map);
    }

    /**
     * 新增车辆统计（按月份）
     * @param page
     * @param size
     * @param month
     * @param map
     * @return
     */
    @RequestMapping("/new_cars")
    public ModelAndView newCars(@RequestParam(value = "page", defaultValue = "1") Integer page,
                                @RequestParam(value = "size", defaultValue = "5") Integer size,
                                @RequestParam(value = "month", required = false) Integer month,
                                Map<String, Object> map) {

        PageRequest pageRequest = PageRequest.of(page - 1, size);
        Page<CarInfo> carInfoPage = null;
        //若月份为空则搜索所有
        if (month == null) {
            carInfoPage = carInfoService.findAll(pageRequest);
        } else{
            carInfoPage = carInfoService.findByMonth(month, pageRequest);
        }
        List<Integer> allByMonth = carInfoService.findAllByMonth();


        if (carInfoPage == null) {
            map.put("msg", "暂无记录！");
            map.put("url", "/diandiancar/admin/index");
            return new ModelAndView("/common/error", map);
        }

        if(month!=null){
            map.put("month", month);

        }
        for(int i=0;i<allByMonth.size();i++){
            map.put("month"+i,allByMonth.get(i));

        }

        map.put("carInfoPage", carInfoPage);
        map.put("currentPage",page);
        map.put("size",size);
        return new ModelAndView("/admin/statistics/new-cars", map);
    }
}
