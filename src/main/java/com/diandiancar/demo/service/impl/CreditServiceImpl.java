package com.diandiancar.demo.service.impl;

import com.diandiancar.demo.repository.CreditRepository;
import com.diandiancar.demo.service.CreditService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.BigInteger;

@Service
public class CreditServiceImpl implements CreditService {

    @Autowired
    private CreditRepository creditRepository;
    @Override
    public BigDecimal getBookMoneyByCreditLevel(Integer creditLevel) {
        return creditRepository.findByCreditLevel(creditLevel).getBookMoney();
    }
}
