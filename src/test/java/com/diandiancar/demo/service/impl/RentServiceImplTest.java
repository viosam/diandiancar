package com.diandiancar.demo.service.impl;

import com.diandiancar.demo.dto.RentDTO;
import com.diandiancar.demo.entity.Rent;
import com.diandiancar.demo.repository.RentRepository;
import com.diandiancar.demo.service.impl.RentServiceImpl;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit4.SpringRunner;
import sun.util.calendar.BaseCalendar;
import sun.util.calendar.LocalGregorianCalendar;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@SpringBootTest
@RunWith(SpringRunner.class)
public class RentServiceImplTest {

    @Autowired
    private RentServiceImpl rentService;

    @Autowired
    private RentRepository rentRepository;

    @Test
    public void findAll() {
        PageRequest pageRequest = PageRequest.of(0, 2);
        List<RentDTO> rentDTOList = rentService.findAll(pageRequest).getContent();
        rentDTOList.stream().forEach(e -> {
            System.out.println(e);
        });
        Assert.assertNotEquals(0, rentDTOList.size());

    }

    @Test
    public void findByCustomerId() {
        PageRequest pageRequest = PageRequest.of(0, 2);
        List<RentDTO> rentDTOList = rentService.findByCustomerId("00001");

        for (RentDTO r : rentDTOList) {
            System.out.println(r.getId());
        }
//        Assert.assertNotEquals(0,rentDTOPage.getTotalElements());
    }

    @Test
    public void findOne() {
        RentDTO rentDTO = rentService.findOne("1550419585173342662");
        Assert.assertNotNull(rentDTO);
    }

    @Test
    public void create() throws Exception {

        Date beginDate = new Date();
        Date endDate = new Date();

        RentDTO rentDTO = new RentDTO();
        rentDTO.setBeginDate(beginDate);
        rentDTO.setEndDate(endDate);
        rentDTO.setBookId("1550413921225825611");
        rentDTO.setCustomerId("00001");
        rentDTO.setPaymentAmountRent(new BigDecimal(100));
        rentDTO.setRentSumDate(1);
        RentDTO result = rentService.create(rentDTO);

        System.out.println(result);
        Assert.assertNotNull(result);

    }

    @Test
    public void cancel() {

        RentDTO rentDTO = rentService.findOne("1550419585173342662");
        RentDTO result = rentService.cancel(rentDTO);

        System.out.println(result);
    }

    @Test
    public void finish() throws Exception {
        RentDTO rentDTO = rentService.findOne("1550419585173342662");
        RentDTO result = rentService.finish(rentDTO);

        System.out.println(result);
    }

    @Test
    public void paid() {
    }

    @Test
    public void test() throws InterruptedException {
        PageRequest pageRequest = PageRequest.of(0, 30);

        Integer month = 1;
        Page<Rent> allByOrderByCreateTimeDesc = rentRepository.findAllByOrderByCreateTimeDesc(pageRequest);
        List<Rent> content = allByOrderByCreateTimeDesc.getContent();
        List<Rent> rentList = new ArrayList<>();
        Calendar calendar = Calendar.getInstance();
        for (Rent r : content) {
            calendar.setTime(r.getCreateTime());
            //calendar.get()获取的月份是从0开始
            int m = calendar.get(Calendar.MONTH) + 1;
            if (m == month) {
                rentList.add(r);
            }
        }

        System.out.println("222222222222");
        for (Rent r :rentList){
            System.out.println(r.getCreateTime()+"==========="+r);
        }

    }

}