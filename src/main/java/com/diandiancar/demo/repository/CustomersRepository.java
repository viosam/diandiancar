package com.diandiancar.demo.repository;

import com.diandiancar.demo.entity.Customer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;


public interface CustomersRepository extends JpaRepository<Customer,String> {

    Customer findByUserNameAndPassword(String userName, String password);

    Customer findByTelNum(String telNum);

    Customer findByUserName(String userName);

    Page<Customer> findByNicknameLike(String nickname, Pageable pageable);
    Page<Customer> findByFreezed(Integer freeze, Pageable pageable);
}
