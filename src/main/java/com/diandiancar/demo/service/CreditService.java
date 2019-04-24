package com.diandiancar.demo.service;

import java.math.BigDecimal;

public interface CreditService {

    BigDecimal getBookMoneyByCreditLevel(Integer creditLevel);
}
