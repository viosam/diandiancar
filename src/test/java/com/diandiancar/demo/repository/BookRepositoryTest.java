package com.diandiancar.demo.repository;

import com.diandiancar.demo.entity.Book;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.junit.Assert.*;

@SpringBootTest
@RunWith(SpringRunner.class)
public class BookRepositoryTest {

    @Autowired
    private BookRepository repository;

    @Test
    public void findAllByOrderByCreateTimeDesc() {
        List<Book> allByOrderByCreateTimeDesc = repository.findAllByOrderByCreateTimeDesc();
        for (Book book: allByOrderByCreateTimeDesc) {
            System.out.println(book);
        }
    }
    @Test
    public void findByCustomerIDOrderByCreateTimeDesc() {
        List<Book> books = repository.findByCustomerIDOrderByCreateTimeDesc("00001");
        for (Book book: books) {
            System.out.println(book);
        }
    }
}