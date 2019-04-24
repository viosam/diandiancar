package com.diandiancar.demo.controller;

import com.diandiancar.demo.service.FileUpload;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/file")
public class FileController {

    @Autowired
    private FileUpload fileUpload;

    @PostMapping("/upload")
    public ModelAndView upload(HttpServletRequest request,
                               Map<String,Object> map){
        //fieldName 是form对应的name
        String fieldName = "field";
        List<String> upload = fileUpload.upload(request, fieldName);

        map.put("url", "/diandiancar/admin/carInfo/index");

        if(upload.size()<0){
            map.put("msg", "上传文件失败！");
            return new ModelAndView("/common/error", map);
        }
        map.put("url", "/diandiancar/admin/carInfo/index");
        return new ModelAndView("/common/success", map);
    }



}
