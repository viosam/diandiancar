package com.diandiancar.demo.repository;

import com.diandiancar.demo.entity.Book;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface BookRepository extends JpaRepository<Book, String> {

    List<Book> findByCustomerIDOrderByCreateTimeDesc(String customerId);
    List<Book> findAllByOrderByCreateTimeDesc();
    Page<Book> findAllByOrderByCreateTimeDesc(Pageable pageable);

    Book findByIdAndCustomerID(String bookId, String customerId);
}
