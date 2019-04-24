package com.diandiancar.demo.service.impl;

import com.diandiancar.demo.dto.BookDTO;
import com.diandiancar.demo.service.impl.BookServiceImpl;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@SpringBootTest
@RunWith(SpringRunner.class)
public class BookServiceImplTest {

    @Autowired
    private BookServiceImpl bookService;

    @Test
    public void findAll() {

        PageRequest pageRequest = PageRequest.of(0,2);
        List<BookDTO> bookDTOList = bookService.findAll(pageRequest).getContent();
        bookDTOList.stream().forEach(e->{
            System.out.println(e);
        });
        Assert.assertNotEquals(0,bookDTOList.size());
    }

    @Test
    public void findByCustomerId() {
        List<BookDTO> bookDTOList = bookService.findByCustomerId("00001");

        Assert.assertNotEquals(0,bookDTOList.size());
    }

    @Test
    public void findOne() {
        BookDTO bookDTO = bookService.findOne("1550413921225825611");
        Assert.assertNotNull(bookDTO);
    }

    @Test
    public void create() throws Exception {
        Date beginDate = new Date();
        Date endDate = new Date();
        String s1 = "2019-02-15 16:43:24";
        String s2 = "2019-02-17 16:43:24";
        SimpleDateFormat sdf =new SimpleDateFormat("yyyy-MM-dd");
        BookDTO bookDTO = new BookDTO();
        bookDTO.setBookBeginDate(sdf.parse(s1));
        bookDTO.setBookEndDate(sdf.parse(s2));
        bookDTO.setEarnestMoney(new BigDecimal(30));
        bookDTO.setCarID("001");
        bookDTO.setCustomerID("00001");
        BookDTO result = bookService.create(bookDTO);

        Assert.assertNotNull(result);
    }

    @Test
    public void cancel() {
        BookDTO bookDTO = bookService.findOne("1550413921225825611");
        BookDTO result = bookService.cancel(bookDTO);

        System.out.println(result);
    }

    @Test
    public void finish() {
        BookDTO bookDTO = bookService.findOne("1550413921225825611");
        BookDTO result = bookService.finish(bookDTO);

        System.out.println(result);
    }

    @Test
    public void testSumTimme() throws ParseException {
        SimpleDateFormat sdf =new SimpleDateFormat("yyyy-MM-dd");

        String date1 = "2019-02-17 22:32:01.0";
        String date2 = "2019-02-19 00:32:01.0";

        Calendar c1 = Calendar.getInstance();
        c1.setTime(sdf.parse(date1));
        Calendar c2 = Calendar.getInstance();
        c2.setTime(sdf.parse(date2));

        long t1 = c1.getTimeInMillis();
        long t2 = c2.getTimeInMillis();

        long days1 = (t2-t1)/(24*60*60*1000);
        System.out.println("相隔的天数="+days1);

        long test = 3L;
        System.out.println(++test);

    }

    @Test
    public void paid() {
    }
}