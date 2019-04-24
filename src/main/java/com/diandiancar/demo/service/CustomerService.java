package com.diandiancar.demo.service;

import com.diandiancar.demo.entity.Customer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CustomerService {

    Customer findCustomerByUserNameAndPassword(Customer customer);

    Customer findCustomerByTelNum(String telNum);

    Customer findCustomerByUserName(String userName);

    Customer create(Customer customer);

    Page<Customer> findAll(Pageable pageable);
    Page<Customer> findByFreezed(Integer freeze, Pageable pageable);

    Page<Customer> findByNicknameLike(String nickname, Pageable pageable);

    Customer freeze(String customerId);

    Customer unFreeze(String customerId);
}
