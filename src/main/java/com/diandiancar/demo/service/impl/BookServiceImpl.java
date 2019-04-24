package com.diandiancar.demo.service.impl;

import com.diandiancar.demo.converter.Book2BookDTOConverter;
import com.diandiancar.demo.converter.Rent2RentDTOConverter;
import com.diandiancar.demo.dto.BookDTO;
import com.diandiancar.demo.dto.RentDTO;
import com.diandiancar.demo.entity.Book;
import com.diandiancar.demo.entity.CarInfo;
import com.diandiancar.demo.entity.Rent;
import com.diandiancar.demo.enums.*;
import com.diandiancar.demo.exception.CarException;
import com.diandiancar.demo.repository.BookRepository;
import com.diandiancar.demo.repository.RentRepository;
import com.diandiancar.demo.service.BookService;
import com.diandiancar.demo.service.CarInfoService;
import com.diandiancar.demo.service.RentService;
import com.diandiancar.demo.util.CalculationDate;
import com.diandiancar.demo.util.KeyUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@Service
@Slf4j
public class BookServiceImpl implements BookService {

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private CarInfoService carInfoService;

    @Autowired
    private RentService rentService;

    @Autowired
    private RentRepository rentRepository;

    @Override
    public Page<BookDTO> findAll(Pageable pageable) {

        Page<Book> bookPage = bookRepository.findAllByOrderByCreateTimeDesc(pageable);
        List<BookDTO> bookDTOList = Book2BookDTOConverter.convert(bookPage.getContent());

        PageImpl<BookDTO> bookDTOPage = new PageImpl<>(bookDTOList, pageable, bookPage.getTotalElements());
        return bookDTOPage;
    }

    @Override
    public List<BookDTO> findByCustomerId(String customerId) {

        if (customerId == null || customerId.equals("")) {
            throw new CarException(ResultEnum.PARAM_ERROR);
        }
        List<Book> bookList = bookRepository.findByCustomerIDOrderByCreateTimeDesc(customerId);
        List<BookDTO> bookDTOList = Book2BookDTOConverter.convert(bookList);

        return bookDTOList;

    }

    @Override
    public BookDTO findOne(String bookId) {
        Book book = bookRepository.findById(bookId).get();
        if (book == null) {
            throw new CarException(ResultEnum.BOOK_NOT_EXIST);
        }
        BookDTO bookDTO = new BookDTO();
        BeanUtils.copyProperties(book, bookDTO);

        return bookDTO;
    }

    @Override
    public BookDTO findByIdAndCustomerID(String bookId, String customerId) {
        Book byIdAndCustomerID = bookRepository.findByIdAndCustomerID(bookId, customerId);
        if (byIdAndCustomerID == null) {
            throw new CarException(ResultEnum.BOOK_NOT_EXIST);
        }
        BookDTO bookDTO = new BookDTO();
        BeanUtils.copyProperties(byIdAndCustomerID, bookDTO);

        return bookDTO;
    }

    @Override
    public BookDTO create(BookDTO bookDTO) throws Exception{

        if (bookDTO == null) {
            throw new CarException(ResultEnum.PARAM_ERROR);
        }
        //判断车辆状态:若车辆状态不是未出租则抛出异常
        CarInfo carInfo = carInfoService.findById(bookDTO.getCarID());
        if(!carInfo.getStatus().equals(CarStatusEnum.NOT_RENT.getCode())){
            throw new CarException(ResultEnum.CAR_RENTED);
        }

        String bookId = KeyUtil.getUniqueKey();
        Book book = new Book();

        BeanUtils.copyProperties(bookDTO, book);
        book.setId(bookId);
        book.setStatus(BookStatusEnum.SUBMIT.getCode());
        book.setPayStatus(PayDepositStatusEnum.WAIT.getCode());

        int sumDate = new Long(CalculationDate.calculationDate(bookDTO.getBookBeginDate(),bookDTO.getBookEndDate())).intValue();
        book.setBookSumDate(sumDate);

        Book result = bookRepository.save(book);

        BookDTO resultBookDTO = new BookDTO();
        BeanUtils.copyProperties(result, resultBookDTO);

        return resultBookDTO;
    }

    @Override
    @Transactional
    public BookDTO cancel(BookDTO bookDTO) {

        if (bookDTO == null) {
            throw new CarException(ResultEnum.PARAM_ERROR);
        }
        //判断book表是否有指定用户id对应bookId的记录，根据用户id和bookId查找book记录
        Book byIdAndCustomerID = bookRepository.findByIdAndCustomerID(bookDTO.getId(), bookDTO.getCustomerID());
        if(byIdAndCustomerID==null){
            log.error("【取消预约】找不到该预约信息，bookDTO={}",bookDTO);
            throw new CarException(ResultEnum.BOOK_NOT_EXIST);
        }
        //若已租借的，则不许取消
        Rent rent = rentRepository.findByBookId(bookDTO.getId());
        if(rent!=null){
            if(rent.getStatus()!=RentStatusEnum.SUBMIT.getCode()){
                log.error("【取消预约】找不到该预约信息，bookDTO={}",bookDTO);
                throw new CarException(ResultEnum.CAR_RENTED);
            }
        }
        //判断预约状态
        if (byIdAndCustomerID.getStatus().equals(BookStatusEnum.CANCEL.getCode())) {
            log.error("【取消预约】预约状态错误，bookId={}, bookStatus={}", bookDTO.getId(), bookDTO.getStatus());
            throw new CarException(ResultEnum.BOOK_STATUS_ERROR);
        }
        //修改预约状态
        byIdAndCustomerID.setStatus(BookStatusEnum.CANCEL.getCode());

        Book updateResult = bookRepository.save(byIdAndCustomerID);
        if(updateResult==null) {
            log.error("【取消预约】更新失败，book={}",bookDTO);
            throw new CarException(ResultEnum.BOOK_UPDAT_FAIL);
        }
        //修改租借状态
        RentDTO rentDTO = Rent2RentDTOConverter.convert(rent);
        System.out.println("book----"+rentDTO);
        rentService.cancel(rentDTO);
        //如果已付预约金，退款
        if(byIdAndCustomerID.getPayStatus()==PayBookStatusEnum.SUCCESS.getCode()){
            byIdAndCustomerID.setPayStatus(PayBookStatusEnum.REFUNDING.getCode());

            //退还预约金
            refundBook(bookDTO);
        }

        //更新车辆状态
        CarInfo carInfo = carInfoService.findById(byIdAndCustomerID.getCarID());
        carInfo.setStatus(CarStatusEnum.NOT_RENT.getCode());
        carInfoService.save(carInfo);
        BookDTO resultBookDTO = new BookDTO();
        BeanUtils.copyProperties(updateResult,resultBookDTO);
        return resultBookDTO;
    }

    //退还预约金
    public BookDTO refundBook(BookDTO bookDTO) {

        Book book = bookRepository.findById(bookDTO.getId()).get();
        book.setPayStatus(PayBookStatusEnum.REFUND_SUCCESSFULLY.getCode());
        bookRepository.save(book);

        BookDTO resultBookDTO = new BookDTO();
        BeanUtils.copyProperties(book, resultBookDTO);
        return resultBookDTO;
    }


    //付款后调用finish()
    @Override
    @Transactional
    public BookDTO finish(BookDTO bookDTO) {

        if (bookDTO == null) {
            throw new CarException(ResultEnum.PARAM_ERROR);
        }

        //根据用户id和bookId查找book记录
        Book byIdAndCustomerID = bookRepository.findByIdAndCustomerID(bookDTO.getId(), bookDTO.getCustomerID());
        if(byIdAndCustomerID==null){
            log.error("【完结预约】找不到该预约信息，bookDTO={}",bookDTO);
            throw new CarException(ResultEnum.BOOK_NOT_EXIST);
        }

        //判断预约状态
        if (!byIdAndCustomerID.getStatus().equals(BookStatusEnum.SUBMIT.getCode())) {
            log.error("【完结预约】预约状态错误，bookId={}, bookStatus={}", bookDTO.getId(), bookDTO.getStatus());
            throw new CarException(ResultEnum.BOOK_STATUS_ERROR);
        }
        //更新预约状态
        byIdAndCustomerID.setStatus(BookStatusEnum.SUCCESS.getCode());
        //更新支付状态
        byIdAndCustomerID.setPayStatus(PayDepositStatusEnum.SUCCESS.getCode());
        //写入预约表
        Book updateResult = bookRepository.save(byIdAndCustomerID);

        if (updateResult == null) {
            log.error("【完结预约】更新失败，book={}", bookDTO);
            throw new CarException(ResultEnum.BOOK_UPDAT_FAIL);
        }

        CarInfo carInfo = carInfoService.findById(bookDTO.getCarID());
        //更新车辆状态
        carInfo.setStatus(CarStatusEnum.TO_BE_OUT_OF_THE_LIBRARY.getCode());
        carInfoService.save(carInfo);

        //写入租借表
        RentDTO rentDTO = new RentDTO();
        rentDTO.setCarId(bookDTO.getCarID());
        rentDTO.setBookId(bookDTO.getId());
        rentDTO.setCarInsurance(bookDTO.getCarInsurance());
        rentDTO.setBeginDate(bookDTO.getBookBeginDate());
        rentDTO.setEndDate(bookDTO.getBookEndDate());
        rentDTO.setRentSumDate(bookDTO.getBookSumDate());
        rentDTO.setPaymentAmountRent(carInfo.getRentPrice()
                .multiply(new BigDecimal(rentDTO.getRentSumDate())));
             rentDTO.setCustomerId(bookDTO.getCustomerID());
        rentDTO.setStatus(RentStatusEnum.SUBMIT.getCode());
        rentDTO.setPayDepositStatus(PayDepositStatusEnum.WAIT.getCode());
        rentDTO.setPaymentAmountDeposit(carInfo.getDeposit());
        rentDTO.setPayRentStatus(PayRentStatusEnum.WAIT.getCode());
        rentDTO.setFromShop(bookDTO.getFromShop());
        try {
            rentService.create(rentDTO);
        } catch (Exception e) {
            log.error("【完结预约】更新失败，book={}", bookDTO);
            throw new CarException(ResultEnum.BOOK_UPDAT_FAIL);
        }
        BookDTO resultBookDTO = new BookDTO();
        BeanUtils.copyProperties(updateResult, resultBookDTO);

        return resultBookDTO;
    }

    @Override
    @Transactional
    public BookDTO paid(BookDTO bookDTO) {
        //TODO 支付
        System.out.println("支付。。。");
        return finish(bookDTO);
    }

}
