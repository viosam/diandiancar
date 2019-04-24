package com.diandiancar.demo.controller;

import com.diandiancar.demo.entity.CarCategory;
import com.diandiancar.demo.entity.CarInfo;
import com.diandiancar.demo.enums.CarDeleteEnum;
import com.diandiancar.demo.enums.CarStatusEnum;
import com.diandiancar.demo.enums.ResultEnum;
import com.diandiancar.demo.exception.CarException;
import com.diandiancar.demo.form.CarInfoForm;
import com.diandiancar.demo.service.CarCategoryService;
import com.diandiancar.demo.service.CarInfoService;
import com.diandiancar.demo.service.FileUpload;
import com.diandiancar.demo.util.KeyUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
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
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Map;

/**
 * 后台车辆
 */
@Controller
@RequestMapping("/admin/carInfo")
public class AdminCarInfoController {

    @Autowired
    private CarInfoService carInfoService;

    @Autowired
    private CarCategoryService carCategoryService;

    @Autowired
    private FileUpload fileUpload;


    @GetMapping("/list")
    public ModelAndView list(@RequestParam(value = "page", defaultValue = "1") Integer page,
                             @RequestParam(value = "size", defaultValue = "5") Integer size,
                             Map<String, Object> map) {
        PageRequest pageRequest = PageRequest.of(page - 1, size);
        Page<CarInfo> carInfoPage = carInfoService.findAll(pageRequest);
        if (carInfoPage == null) {
            map.put("msg", "查找结果为空，请添加车辆信息！");
            map.put("url", "/diandiancar/admin/carInfo/save");
            return new ModelAndView("/common/error", map);
        }
        map.put("carInfoPage", carInfoPage);
        map.put("currentPage", page);
        map.put("size", size);

        //因为search()也是跳转到list页面，所以判断若是search调用，则分页按钮跳转到Controller的search()
        map.put("searchTag","notSearch");

        return new ModelAndView("/admin/carInfo/car-list", map);
    }

    //车辆信息修改页面（carId有值就做数据回显（车辆修改））
    @GetMapping("/index")
    public ModelAndView index(@RequestParam(value = "carId", required = false) String carId,
                              Map<String, Object> map) {

        System.out.println("index  in");
        if (!StringUtils.isEmpty(carId)) {
            CarInfo carInfo = carInfoService.findById(carId);
            map.put("carInfo", carInfo);
        }

        //查找所有车辆类型
        List<CarCategory> categoryList = carCategoryService.findAll();
        map.put("categoryList", categoryList);
        return new ModelAndView("/admin/carInfo/car-add", map);
    }


    @PostMapping("/save")
    public ModelAndView save(@Valid CarInfoForm carInfoForm,
                             BindingResult bindingResult,
                             HttpServletRequest request,
                             Map<String, Object> map) {

        if (bindingResult.hasErrors()) {
            map.put("msg", bindingResult.getFieldError().getDefaultMessage());
            map.put("url", "/diandiancar/admin/carInfo/index");
            return new ModelAndView("/common/error", map);
        }
        CarInfo carInfo = new CarInfo();

        try {
            //如果carId不为空，说明是更新
            if (!StringUtils.isEmpty(carInfoForm.getCarId())) {
                carInfo = carInfoService.findById(carInfoForm.getCarId());
                //如果车辆正在使用则不能修改
                if(carInfo.getStatus()==CarStatusEnum.RENTED.getCode()){
                    throw new CarException(ResultEnum.CAR_USED);
                }
                carInfoForm.setStatus(carInfo.getStatus());
            } else {
                carInfoForm.setCarId(KeyUtil.getUniqueKey());
            }

            BeanUtils.copyProperties(carInfoForm, carInfo);
            carInfo.setDeleted(CarDeleteEnum.NOT_DELETE.getCode());
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            carInfo.setProductTime(sdf.parse(carInfoForm.getProductTime()));

            //上传图片,并获取文件路径
            List<String> resultUrl = fileUpload.upload(request,"carIconFile");
            System.out.println(resultUrl.get(0));
            carInfo.setCarIcon(resultUrl.get(0));
            carInfoService.save(carInfo);

        } catch (Exception e) {
            map.put("msg", e.getMessage());
            map.put("url", "/diandiancar/admin/carInfo/list");
            return new ModelAndView("/common/error", map);
        }

        map.put("url", "/diandiancar/admin/carInfo/list");
        return new ModelAndView("/common/success", map);
    }

    @GetMapping("/search")
    public ModelAndView search(@RequestParam(value = "carName", required = false) String carName,
                               @RequestParam(value = "page", defaultValue = "1") Integer page,
                               @RequestParam(value = "size", defaultValue = "5") Integer size,
                               Map<String, Object> map) {

        PageRequest pageRequest = PageRequest.of(page - 1, size);

        if (StringUtils.isEmpty(carName) || carName == null) {
            Page<CarInfo> carInfoPage = carInfoService.findAll(pageRequest);
            if (carInfoPage == null) {
                map.put("msg", "查找结果为空，请添加车辆信息！");
                map.put("url", "/diandiancar/admin/carInfo/save");
                return new ModelAndView("/common/error", map);
            }
            map.put("carInfoPage", carInfoPage);
            map.put("currentPage", page);
            map.put("size", size);
        } else {
            Page<CarInfo> carInfoPage = carInfoService.findByCarNameLike("%"+carName+"%", pageRequest);
            if(carInfoPage==null){
                map.put("msg","查找:"+carName+",的结果为空");
                map.put("url", "/diandiancar/admin/carInfo/list");
                return new ModelAndView("/common/error",map);
            }
            map.put("carInfoPage", carInfoPage);
            map.put("currentPage", page);
            map.put("size", size);
        }
        //判断分页按钮跳转到Controller的search()
        map.put("searchTag","search");
        //分页按钮的请求参数
        if(carName!=null){
            map.put("carName",carName);
        }

        return new ModelAndView("/admin/carInfo/car-list", map);
    }

    @RequestMapping("/delete")
    public ModelAndView delete(@RequestParam("carId") String customerId,
                               Map<String, Object> map) {


        try {
            carInfoService.delete(customerId);
        } catch (Exception e) {
            map.put("msg", e.getMessage());
            map.put("url", "/diandiancar/admin/carInfo/list");
            return new ModelAndView("/common/error", map);
        }
        map.put("url", "/diandiancar/admin/carInfo/list");
        return new ModelAndView("/common/success", map);
    }

    //车辆详情信息
    @GetMapping("/detail")
    public ModelAndView detail(@RequestParam(value = "carId", required = false) String carId,
                              Map<String, Object> map) {

        if (!StringUtils.isEmpty(carId)) {
            CarInfo carInfo = carInfoService.findById(carId);
            map.put("carInfo", carInfo);
        }

        return new ModelAndView("/admin/carInfo/car-detail", map);
    }

//    @RequestMapping("/admin_index")
//    public ModelAndView test() {
//        System.out.println("index");
//        return new ModelAndView("/admin/index");
//    }

}
