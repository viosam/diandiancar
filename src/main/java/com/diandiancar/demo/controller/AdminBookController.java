package com.diandiancar.demo.controller;

import com.diandiancar.demo.dto.BookDTO;
import com.diandiancar.demo.enums.ResultEnum;
import com.diandiancar.demo.service.BookService;
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
@RequestMapping("/admin/book")
@Slf4j
public class AdminBookController {

    @Autowired
    private BookService bookService;

    @GetMapping("/list")
    public ModelAndView list(@RequestParam(value = "page", defaultValue = "1") Integer page,
                             @RequestParam(value = "size", defaultValue = "5") Integer size,
                             Map<String, Object> map) {

        PageRequest pageRequest = PageRequest.of(page - 1, size);
        Page<BookDTO> bookDTOPage = bookService.findAll(pageRequest);
        map.put("bookDTOPage", bookDTOPage);
        map.put("currentPage", page);
        map.put("size", size);
        return new ModelAndView("/admin/book/list");
    }


    public void test1(){

    }
    @GetMapping("/finish")
    public ModelAndView finish(@RequestParam("bookId") String bookId,
                               Map<String, Object> map) {

        BookDTO bookDTO = null;
        try {
            bookDTO = bookService.findOne(bookId);
            bookService.finish(bookDTO);
        } catch (Exception e) {
            log.error("【管理员完结预约】发生异常，{}", e);
            map.put("msg", e.getMessage());
            map.put("url", "/diandiancar/admin/book/list");
            return new ModelAndView("/common/error", map);
        }
        map.put("msg", ResultEnum.BOOK_FINISH_SUCCESS.getMessage());
        map.put("url", "/diandiancar/admin/book/list");
        return new ModelAndView("/common/success", map);

    }

    @GetMapping("/cancel")
    public ModelAndView cancel(@RequestParam("bookId") String bookId,
                               Map<String, Object> map) {

        BookDTO bookDTO = null;
        try {
            bookDTO = bookService.findOne(bookId);
            bookService.cancel(bookDTO);
        } catch (Exception e) {
            log.error("【管理员取消预约】发生异常，{}", e);
            map.put("msg", e.getMessage());
            map.put("url", "/diandiancar/admin/book/list");
            return new ModelAndView("/common/error", map);
        }
        map.put("msg", ResultEnum.BOOK_CANCEL_SUCCESS.getMessage());
        map.put("url", "/diandiancar/admin/book/list");
        return new ModelAndView("/common/success", map);

    }
}
