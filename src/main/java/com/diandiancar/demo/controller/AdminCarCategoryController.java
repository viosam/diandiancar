package com.diandiancar.demo.controller;

import com.diandiancar.demo.entity.CarCategory;
import com.diandiancar.demo.entity.CarInfo;
import com.diandiancar.demo.form.CarCategoryForm;
import com.diandiancar.demo.service.CarCategoryService;
import com.diandiancar.demo.service.CarInfoService;
import lombok.extern.slf4j.Slf4j;
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

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/admin/carCategory")
@Slf4j
public class AdminCarCategoryController {

    @Autowired
    CarCategoryService carCategoryService;

    @Autowired
    CarInfoService carInfoService;

    @GetMapping("/list")
    public ModelAndView list(@RequestParam(value = "page", defaultValue = "1") Integer page,
                             @RequestParam(value = "size", defaultValue = "5") Integer size,
                             Map<String, Object> map) {
        PageRequest pageRequest = PageRequest.of(page - 1, size);
        Page<CarCategory> carCategoryPage = carCategoryService.findAll(pageRequest);
        map.put("carCategoryPage", carCategoryPage);
        map.put("currentPage", page);
        map.put("size", size);

        //因为search()也是跳转到list页面，所以判断若是search调用，则分页按钮跳转到Controller的search()
        map.put("searchTag","notSearch");
        return new ModelAndView("/admin/category/category-list", map);
    }

    //车辆类型修改页面（carCategoryId有值就做数据回显（类型修改））
    @GetMapping("/index")
    public ModelAndView index(@RequestParam(name = "carCategoryId", required = false) Integer carCategoryId,
                              Map<String, Object> map) {
        if (carCategoryId != null) {
            CarCategory category = carCategoryService.findOne(carCategoryId);
            map.put("category", category);
        }
        return new ModelAndView("/admin/category/category-add", map);
    }

    //更新、新增
    @PostMapping("/save")
    public ModelAndView save(@Valid CarCategoryForm form,
                             BindingResult bindingResult,
                             Map<String, Object> map) {

        if (bindingResult.hasErrors()) {
            map.put("msg", bindingResult.getFieldError().getDefaultMessage());
            map.put("url", "/diandiancar/admin/carCategory/index");
            return new ModelAndView("/common/error", map);
        }
        String trimCategoryName = StringUtils.trimAllWhitespace(form.getCategoryName());

        if (StringUtils.isEmpty(form.getCategoryType())||StringUtils.isEmpty(trimCategoryName)) {
            map.put("msg", "车辆类型或类型编号不能为空!!");
            map.put("url", "/diandiancar/admin/carCategory/index");
            return new ModelAndView("/common/error", map);
        }

        CarCategory carCategory = new CarCategory();
        try {
            //tag:标记，若修改已有的类型，则修改carInfo该类型
            boolean tag = false;
            //记录旧的categoryType，根据这个修改carInfo对应的categoryType
            Integer oldCategoryType = null;

            if (form.getCategoryId() != null) {
                tag = true;
                carCategory = carCategoryService.findOne(form.getCategoryId());
                oldCategoryType = carCategory.getCategoryType();
            }
            BeanUtils.copyProperties(form, carCategory);
            carCategory.setCategoryName(trimCategoryName);

            carCategoryService.save(carCategory);
            //修改影响到的车辆的类型
            if (tag) {
                List<CarInfo> carInfoList = carInfoService.findByCategoryType(oldCategoryType);
                if(carInfoList.size()>0){
                    List<String> carIdList = new ArrayList<>();
                    carInfoList.stream().map(e -> carIdList.add(e.getCarId())).collect(Collectors.toList());
                    int updateCarInfoResult = carInfoService.updateCategoryByCarIds(form.getCategoryType(), carIdList);
                }
            }

        } catch (Exception e) {
            map.put("msg", e.getMessage());
            map.put("url", "/diandiancar/admin/carCategory/index");
            return new ModelAndView("/common/error", map);
        }
        map.put("url", "/diandiancar/admin/carCategory/list");
        return new ModelAndView("/common/success", map);
    }

    @GetMapping("/search")
    public ModelAndView search(@RequestParam(value = "categoryName", required = false) String categoryName,
                               @RequestParam(value = "page", defaultValue = "1") Integer page,
                               @RequestParam(value = "size", defaultValue = "5") Integer size,
                               Map<String, Object> map) {

        String trimCategoryName = StringUtils.trimAllWhitespace(categoryName);

        PageRequest pageRequest = PageRequest.of(page - 1, size);

        if (StringUtils.isEmpty(trimCategoryName)) {
            Page<CarCategory> carCategoryPage = carCategoryService.findAll(pageRequest);
            if (carCategoryPage == null) {
                map.put("msg", "查找结果为空，请添加类型信息！");
                map.put("url", "/diandiancar/admin/carCategory/save");
                return new ModelAndView("/common/error", map);
            }
            map.put("carCategoryPage", carCategoryPage);
            map.put("currentPage", page);
            map.put("size", size);
        } else {
            Page<CarCategory> carCategoryPage = carCategoryService.
                    findByCategoryNameLike("%"+trimCategoryName+"%", pageRequest);
            if (carCategoryPage == null) {
                map.put("msg", "查找结果为空");
                map.put("url", "/diandiancar/admin/carCategory/list");
                return new ModelAndView("/common/error", map);
            }
            map.put("carCategoryPage", carCategoryPage);
            map.put("currentPage", page);
            map.put("size", size);
        }
        //判断分页按钮跳转到Controller的search()
        map.put("searchTag","search");
        //分页按钮的请求参数
        if(categoryName!=null){
            map.put("categoryName",categoryName);
        }

        return new ModelAndView("/admin/category/category-list", map);

    }
}
