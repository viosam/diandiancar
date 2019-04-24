package com.diandiancar.demo.service;

import com.diandiancar.demo.dto.BookDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface BookService {

    //管理员查找所有人的预约记录
    Page<BookDTO> findAll(Pageable pageable);

    //用户查找预约列表
    List<BookDTO> findByCustomerId(String customerId);

    //查找单个记录
    BookDTO findOne(String bookId);

    //根据用户id和bookId查找
    BookDTO findByIdAndCustomerID(String id,String customerId);

    //创建预约
    BookDTO create(BookDTO bookDTO)throws Exception;

    //取消预约
    BookDTO cancel(BookDTO bookDTO);

    //预约完成
    BookDTO finish(BookDTO bookDTO);

    //支付预约金
    BookDTO paid(BookDTO bookDTO);

    //退还预约金
    public BookDTO refundBook(BookDTO bookDTO);
}
