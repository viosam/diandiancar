package com.diandiancar.demo.service.impl;

import com.diandiancar.demo.entity.Customer;
import com.diandiancar.demo.enums.CreaditLevelEnum;
import com.diandiancar.demo.enums.CustomerFreezedEnum;
import com.diandiancar.demo.enums.ResultEnum;
import com.diandiancar.demo.exception.CarException;
import com.diandiancar.demo.repository.CustomersRepository;
import com.diandiancar.demo.service.CustomerService;
import com.diandiancar.demo.util.KeyUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class CustomerServiceImpl implements CustomerService {

    @Autowired
    private CustomersRepository repository;

    @Override
    public Customer findCustomerByUserNameAndPassword(Customer customer) {

        if (customer == null || StringUtils.isEmpty(customer.getUserName()) || StringUtils.isEmpty(customer.getPassword())) {
            throw new CarException(ResultEnum.PARAM_ERROR);
        }
        String userName = customer.getUserName();
        String password = customer.getPassword();

        return repository.findByUserNameAndPassword(userName, password);
    }

    public Customer findCustomerByTelNum(String telNum) {
        if (telNum == null || telNum.equals("")) {
            throw new CarException(ResultEnum.PARAM_ERROR);
        }
        if (!isPhone(telNum)) {
            throw new CarException(ResultEnum.PHONE_NUM_ERROR);
        }
        return repository.findByTelNum(telNum);
    }

    @Override
    public Customer findCustomerByUserName(String userName) {
        if (userName == null || userName.equals("")) {
            throw new CarException(ResultEnum.PARAM_ERROR);
        }

        return repository.findByUserName(userName);
    }

    @Override
    public Customer create(Customer customer) {

        //TODO 校验userName，password
        // 校验手机号码
//        if(!isPhone(customer.getTelNum())){
//            throw new CarException(ResultEnum.PHONE_NUM_ERROR);
//        }

        Customer byUserName = repository.findByUserName(customer.getUserName());
        if(byUserName!=null){
            throw new CarException(ResultEnum.USER_NAME_USED);
        }
        Customer byTelNum = repository.findByTelNum(customer.getTelNum());
        if (byTelNum!=null){
            throw new CarException(ResultEnum.PHONE_NUM_USED);
        }

        String customerId = KeyUtil.getUniqueKey();
        customer.setId(customerId);
        customer.setCreditLevel(CreaditLevelEnum.LEVEL_PLATINUM.getCode());
        customer.setFreezed(CustomerFreezedEnum.NOT_FREEZED.getCode());
        Customer result = repository.save(customer);
        return result;
    }

    @Override
    public Page<Customer> findAll(Pageable pageable) {
        return repository.findAll(pageable);
    }

    @Override
    public Page<Customer> findByFreezed(Integer freeze, Pageable pageable) {
        return repository.findByFreezed(freeze,pageable);
    }

    @Override
    public Page<Customer> findByNicknameLike(String nickname, Pageable pageable){
        return repository.findByNicknameLike(nickname,pageable);
    }

    @Override
    public Customer freeze(String customerId) {
        Customer customer = repository.findById(customerId).get();

        if(customer==null){
            throw new CarException(ResultEnum.USER_NOT_EXIST);
        }
        if(customer.getFreezed()==CustomerFreezedEnum.FREEZED.getCode()){
            throw new CarException(ResultEnum.USER_FREEZE_STATUS_FALSE);
        }
        customer.setFreezed(CustomerFreezedEnum.FREEZED.getCode());
        return repository.save(customer);
    }

    @Override
    public Customer unFreeze(String customerId) {
        Customer customer = repository.findById(customerId).get();

        if(customer==null){
            throw new CarException(ResultEnum.USER_NOT_EXIST);
        }
        if(customer.getFreezed()==CustomerFreezedEnum.NOT_FREEZED.getCode()){
            throw new CarException(ResultEnum.USER_UNFREEZE_STATUS_FALSE);
        }
        customer.setFreezed(CustomerFreezedEnum.NOT_FREEZED.getCode());
        return repository.save(customer);
    }

    public static boolean isPhone(String telNum) {
        String regex = "^((13[0-9])|(14[5,7,9])|(15([0-3]|[5-9]))|(166)|(17[0,1,3,5,6,7,8])|(18[0-9])|(19[8|9]))\\d{8}$";
        if (telNum.length() != 11) {
            return false;
        } else {
            Pattern p = Pattern.compile(regex);
            Matcher m = p.matcher(telNum);
            boolean isMatch = m.matches();

            return isMatch;
        }
    }
}
