package com.diandiancar.demo.service.impl;

import com.diandiancar.demo.enums.ResultEnum;
import com.diandiancar.demo.exception.CarException;
import com.diandiancar.demo.service.FileUpload;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class FileUploadImpl implements FileUpload {

    /**
     * 返回文件路径
     *
     * @param request
     * @param fieldName 是form对应的name
     * @return
     */
    @Override
    public List<String> upload(HttpServletRequest request, String fieldName) {
        System.out.println("in upload");

        List<String> fileUrlList = new ArrayList<>();

//        String fileupload = request.getSession().getServletContext().getRealPath("fileupload");

        List<MultipartFile> files = ((MultipartHttpServletRequest) request).getFiles(fieldName);

        if (files.size() <= 0) {
            log.error("【上传文件】上传文件数量少于1");
            throw new CarException(ResultEnum.FILE_UPLOAD_ERROR);
        }

        //获取项目真实路径
        String realPath = request.getServletContext().getRealPath("/fileupload/");
//        String contextPath = request.getContextPath();

        for (MultipartFile multipartFile : files) {
            //如果文件名为空，continue
            if (multipartFile.getOriginalFilename() == null || multipartFile.getOriginalFilename().equals("")) {
                System.out.println("continue");
                continue;
            }
            if (multipartFile.isEmpty()) {
                log.error("【上传文件】" + "上传文件:" + multipartFile.getOriginalFilename() + "为空");
                throw new CarException(ResultEnum.FILE_UPLOAD_ERROR);
            }

            //获取上传文件的类型
            String contentType = multipartFile.getContentType();
            //判断文件是类型是否是图片类型
            if (!(contentType.equals("image/jpeg") || contentType.equals("image/png"))) {
                log.error("【上传文件】" + "类型" + contentType + "错误");
                throw new CarException(ResultEnum.FILE_UPLOAD_ERROR);
            }
            //原文件名
            String fileName = multipartFile.getOriginalFilename();
            File file = new File(realPath);
            if(!file.exists()){
                file.mkdir();
            }
            File dest = new File(realPath + fileName);
            try {
                multipartFile.transferTo(dest);
                //将路径添加到list
                fileUrlList.add("/diandiancar/fileupload/" + fileName);
//                return "上传成功";
            } catch (IOException e) {
                e.printStackTrace();
                log.error("【上传文件】" + "错误:" + e.getMessage());
                throw new CarException(ResultEnum.FILE_UPLOAD_ERROR);
            }
        }
        return fileUrlList;
    }
}
