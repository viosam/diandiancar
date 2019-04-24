package com.diandiancar.demo.controller;

import com.diandiancar.demo.VO.ResultVO;
import com.diandiancar.demo.entity.CarInfo;
import com.diandiancar.demo.entity.Carousel;
import com.diandiancar.demo.enums.CarDeleteEnum;
import com.diandiancar.demo.enums.ResultEnum;
import com.diandiancar.demo.form.CarouselForm;
import com.diandiancar.demo.service.CarInfoService;
import com.diandiancar.demo.service.CarouselService;
import com.diandiancar.demo.service.FileUpload;
import com.diandiancar.demo.util.KeyUtil;
import com.diandiancar.demo.util.ResultVOUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/carousel")
public class CarouselController {

    @Autowired
    private CarouselService carouselService;

    @Autowired
    private FileUpload fileUpload;

    @Autowired
    private CarInfoService carInfoService;

    @GetMapping("/list")
    @ResponseBody
    public ResultVO list() {

        //查找未删除的
        List<Carousel> carouselList = carouselService.findRentable();

        if (carouselList.size() > 0) {
            return ResultVOUtil.success(carouselList);
        }

        return ResultVOUtil.error(1, "暂无轮播图");
    }

    @RequestMapping("/admin_list")
    public ModelAndView adminList(Map<String, Object> map) {

        List<Carousel> carouselList = carouselService.findAll();
        if (carouselList.size() <= 0) {
            map.put("msg", "查无轮播图,请添加！");
            map.put("url", "/diandiancar/carousel/index");
            return new ModelAndView("/common/error", map);

        }
        map.put("carouselList", carouselList);
        return new ModelAndView("/admin/carousel/carousel-list", map);

    }

    @PostMapping("/save")
    public ModelAndView save(@Valid CarouselForm carouselForm,
                             BindingResult bindingResult,
                             HttpServletRequest request,
                             Map<String, Object> map) {

        if (bindingResult.hasErrors()) {
            map.put("msg", bindingResult.getFieldError().getDefaultMessage());
            map.put("url", "/diandiancar/carousel/index");
            return new ModelAndView("/common/error", map);
        }
        String trimCarId = StringUtils.trimAllWhitespace(carouselForm.getCarId());

        if (StringUtils.isEmpty(trimCarId)) {
            map.put("msg", ResultEnum.PARAM_ERROR);
            map.put("url", "/diandiancar/carousel/index");
            return new ModelAndView("/common/error", map);
        }

        CarInfo carInfoByCarId = carInfoService.findById(trimCarId);
        if (carInfoByCarId==null) {
            map.put("msg", ResultEnum.CAR_NOT_EXIST);
            map.put("url", "/diandiancar/carousel/index");
            return new ModelAndView("/common/error", map);
        }

        Carousel carousel = new Carousel();
        carousel.setCarId(carInfoByCarId.getCarId());

        String trimDescription = StringUtils.trimLeadingWhitespace(carouselForm.getDescription());
        if (!StringUtils.isEmpty(trimDescription)) {
            carousel.setDescription(trimDescription);
        }

        try {
            //上传图片,并获取文件路径
            List<String> resultUrl = fileUpload.upload(request, "carouselPic");
            carousel.setPicUrl(resultUrl.get(0));
            if(StringUtils.isEmpty(carouselForm.getId())){
                carousel.setId(KeyUtil.getUniqueKey());

            }else {
                carousel.setId(carouselForm.getId());
            }
            carouselService.save(carousel);
        } catch (Exception e) {
            map.put("msg", e.getMessage());
            map.put("url", "/diandiancar/carousel/admin_list");
            return new ModelAndView("/common/error", map);
        }
        map.put("url", "/diandiancar/carousel/admin_list");
        return new ModelAndView("/common/success", map);
    }

    //轮播图新增/修改页面
    @RequestMapping("/index")
    public ModelAndView index(@RequestParam(value = "id", required = false) String id,
                              Map<String, Object> map) {
        if (!StringUtils.isEmpty(id)) {
            Carousel carousel = carouselService.findById(id);
            map.put("carousel", carousel);
        }
        return new ModelAndView("/admin/carousel/carousel-add", map);
    }

    @RequestMapping("/delete")
    public ModelAndView delete(@RequestParam("id") String id,
                               Map<String, Object> map) {

        try {
            carouselService.delete(id);
        } catch (Exception e) {
            map.put("msg", e.getMessage());
            map.put("url", "/diandiancar/carousel/admin_list");
            return new ModelAndView("/common/error", map);
        }
        map.put("url", "/diandiancar/carousel/admin_list");
        return new ModelAndView("/common/success", map);
    }

}
