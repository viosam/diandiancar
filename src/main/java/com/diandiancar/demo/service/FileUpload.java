package com.diandiancar.demo.service;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public interface FileUpload {

    /**
     *
     * @param request
     * @param fieldName form表单对应的name
     * @return
     */
    List<String> upload(HttpServletRequest request, String fieldName);
}
