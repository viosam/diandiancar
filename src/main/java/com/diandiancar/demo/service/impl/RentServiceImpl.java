package com.diandiancar.demo.service.impl;

import com.diandiancar.demo.VO.StatisticsCarInOutLib;
import com.diandiancar.demo.converter.Rent2RentDTOConverter;
import com.diandiancar.demo.dto.RentDTO;
import com.diandiancar.demo.entity.Book;
import com.diandiancar.demo.entity.CarInfo;
import com.diandiancar.demo.entity.Rent;
import com.diandiancar.demo.enums.*;
import com.diandiancar.demo.exception.CarException;
import com.diandiancar.demo.repository.BookRepository;
import com.diandiancar.demo.repository.CarInfoRepository;
import com.diandiancar.demo.repository.RentRepository;
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
import org.springframework.util.StringUtils;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class RentServiceImpl implements RentService {

    @Autowired
    private RentRepository rentRepository;

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private CarInfoRepository carInfoRepository;

    @Override
    public Page<RentDTO> findAll(Pageable pageable) {
        Page<Rent> rentPage = rentRepository.findAllByOrderByCreateTimeDesc(pageable);
        List<RentDTO> rentDTOList = Rent2RentDTOConverter.convert(rentPage.getContent());
        PageImpl<RentDTO> rentDTOPage = new PageImpl<>(rentDTOList, pageable, rentPage.getTotalElements());
        return rentDTOPage;
    }

    @Override
    public Page<RentDTO> findByMonth(Integer month, Pageable pageable) {

        //1.查找所有数据
        Page<Rent> allByOrderByCreateTimeDesc = rentRepository.findAllByOrderByCreateTimeDesc(pageable);
        List<Rent> content = allByOrderByCreateTimeDesc.getContent();
        List<Rent> rentList = new ArrayList<>();

        //2. 比较记录的create_time 是否等于month
        Calendar calendar = Calendar.getInstance();
        for (Rent r : content) {
            calendar.setTime(r.getCreateTime());
            //calendar.get()获取的月份是从0开始
            int m = calendar.get(Calendar.MONTH) + 1;
            //3. 将数据保存到列表
            if (m == month) {
                rentList.add(r);
            }
        }

        List<RentDTO> rentDTOList = Rent2RentDTOConverter.convert(rentList);
        PageImpl<RentDTO> rentDTOPage = new PageImpl<>(rentDTOList, pageable, allByOrderByCreateTimeDesc.getTotalElements());
        return rentDTOPage;
    }

    @Override
    public StatisticsCarInOutLib findByCarStatus(Integer status, Pageable pageable) {

        //根据status查找车辆
        Page<CarInfo> byStatus = carInfoRepository.findByStatus(status, pageable);
        //获取车辆的id
        List<String> carIds = byStatus.getContent().stream().map(e -> e.getCarId()).collect(Collectors.toList());
        //根据车辆id查找rent
        Page<Rent> rentByCarIdIn = rentRepository.findByCarIdIn(carIds, pageable);


        List<RentDTO> rentDTOList = Rent2RentDTOConverter.convert(rentByCarIdIn.getContent());
        PageImpl<RentDTO> rentDTOPage = new PageImpl<>(rentDTOList, pageable, rentByCarIdIn.getTotalElements());

        //获取已归还/未归还的车辆数
        List<CarInfo> carInfoList = carInfoRepository.findAll();
        Integer returned = 0;
        Integer notReturend = 0;
        for(CarInfo carInfo:carInfoList){
            if(carInfo.getStatus()==CarStatusEnum.NO_RETURNED.getCode()){
                ++notReturend;
            }
            if(carInfo.getStatus()==CarStatusEnum.RETURNED.getCode()){
                ++returned;
            }
        }
        StatisticsCarInOutLib statisticsCarInOutLib = new StatisticsCarInOutLib();
        statisticsCarInOutLib.setNotReturend(notReturend);
        statisticsCarInOutLib.setReturned(returned);
        statisticsCarInOutLib.setRentDTOPage(rentDTOPage);

        return statisticsCarInOutLib;
    }


    @Override
    public List<RentDTO> findByCustomerId(String customerId) {
        if (customerId == null && customerId.equals("")) {
            throw new CarException(ResultEnum.PARAM_ERROR);
        }
        List<Rent> rentList = rentRepository.findByCustomerIdOrderByCreateTimeDesc(customerId);
        List<RentDTO> rentDTOList = Rent2RentDTOConverter.convert(rentList);
        return rentDTOList;

    }

    @Override
    public RentDTO findOne(String rentId) {
        if (rentId == null && rentId.equals("")) {
            throw new CarException(ResultEnum.PARAM_ERROR);
        }
        Rent rent = rentRepository.findById(rentId).get();
        if (rent == null) {
            throw new CarException(ResultEnum.RENT_NOT_EXIST);
        }
        RentDTO rentDTO = new RentDTO();
        BeanUtils.copyProperties(rent, rentDTO);

        return rentDTO;
    }

    @Override
    public RentDTO findByIdAndCustomerId(String rentId, String customerId) {
        if (StringUtils.isEmpty(rentId) && StringUtils.isEmpty(customerId)) {
            throw new CarException(ResultEnum.PARAM_ERROR);
        }
        Rent rent = rentRepository.findByIdAndCustomerId(rentId, customerId);
        if (rent == null) {
            throw new CarException(ResultEnum.RENT_NOT_EXIST);
        }
        RentDTO rentDTO = new RentDTO();
        BeanUtils.copyProperties(rent, rentDTO);

        return rentDTO;
    }

    @Override
    public RentDTO create(RentDTO rentDTO) throws Exception {

        if (rentDTO == null) {
            throw new CarException(ResultEnum.PARAM_ERROR);
        }
        //根据customerId跟bookId查找book表
        Book bookByIdAndCustomerID = bookRepository.findByIdAndCustomerID(rentDTO.getBookId(), rentDTO.getCustomerId());
        if (bookByIdAndCustomerID == null) {
            log.error("【创建租借】用户未预约该车辆，rentDTO={},", rentDTO);
            throw new CarException(ResultEnum.BOOK_NOT_EXIST);
        }
        String rentId = KeyUtil.getUniqueKey();
        Rent rent = new Rent();
        BeanUtils.copyProperties(rentDTO, rent);
        rent.setId(rentId);
        rent.setStatus(RentStatusEnum.SUBMIT.getCode());
        rent.setPayDepositStatus(PayDepositStatusEnum.WAIT.getCode());
        rent.setPayRentStatus(PayRentStatusEnum.WAIT.getCode());
        int sumDate = new Long(CalculationDate.calculationDate(rent.getBeginDate(), rent.getEndDate())).intValue();
        if (sumDate <= 0) {
            throw new CarException(ResultEnum.RENT_TIME_HAS_ERROR);
        }
        rent.setRentSumDate(sumDate);
        Rent result = rentRepository.save(rent);
        RentDTO resultRentDTO = new RentDTO();
        BeanUtils.copyProperties(result, resultRentDTO);

        return resultRentDTO;
    }

    //支付押金
    @Transactional
    @Override
    public RentDTO paidDeposit(RentDTO rentDTO) {

        if (rentDTO == null) {
            throw new CarException(ResultEnum.PARAM_ERROR);
        }

        //判断租借表是否有指定用户id对应rentId的信息，根据用户id跟rentId查找rent记录
        CarInfo carInfoById = carInfoRepository.findById(rentDTO.getCarId()).get();
        Rent rentByIdAndCustomerId = rentRepository.findByIdAndCustomerId(rentDTO.getId(), rentDTO.getCustomerId());
        if (rentByIdAndCustomerId == null || carInfoById == null) {
            log.error("【租借支付押金】支付押金失败，rentDTO={}", rentDTO);
            throw new CarException(ResultEnum.RENT_NOT_EXIST);
        }

        //判断租借状态
        if (!rentDTO.getStatus().equals(RentStatusEnum.SUBMIT.getCode())) {
            log.error("【租借支付押金】 租借状态错误，rentId={},rentStatus={}", rentDTO.getId(), rentDTO.getStatus());
            throw new CarException(ResultEnum.RENT_STATUS_ERROR);
        }

        //车辆状态修改状态、租借支付押金状态
        carInfoById.setStatus(CarStatusEnum.RENTED.getCode());
        rentByIdAndCustomerId.setPayDepositStatus(PayDepositStatusEnum.SUCCESS.getCode());

        Rent resultRent = rentRepository.save(rentByIdAndCustomerId);
        CarInfo resultCarInfo = carInfoRepository.save(carInfoById);
        if (resultRent == null) {
            log.error("【租借支付押金】支付押金失败，rent={}", rentDTO);
            throw new CarException(ResultEnum.RENT_UPDATE_FALL);
        }
        return Rent2RentDTOConverter.convert(resultRent);
    }

    //支付租金
    @Transactional
    @Override
    public RentDTO paidRent(RentDTO rentDTO) throws Exception {
        if (rentDTO == null) {
            throw new CarException(ResultEnum.PARAM_ERROR);
        }

        //判断租借表是否有指定用户id对应rentId的信息，根据用户id跟rentId查找rent记录
        CarInfo carInfoById = carInfoRepository.findById(rentDTO.getCarId()).get();
        Rent rentByIdAndCustomerId = rentRepository.findByIdAndCustomerId(rentDTO.getId(), rentDTO.getCustomerId());
        if (rentByIdAndCustomerId == null || carInfoById == null) {
            log.error("【租借支付租金】支付失败，rentDTO={}", rentDTO);
            throw new CarException(ResultEnum.RENT_NOT_EXIST);
        }

        //判断租借状态
        if (!rentByIdAndCustomerId.getStatus().equals(RentStatusEnum.SUBMIT.getCode())
                || rentByIdAndCustomerId.getPayDepositStatus() != PayDepositStatusEnum.SUCCESS.getCode()) {
            log.error("【租借支付租金】 租借状态或押金支付状态错误，rentDTO={}", rentDTO);
            throw new CarException(ResultEnum.RENT_STATUS_ERROR);
        }

        //租借结束日根据实际归还时间
        rentByIdAndCustomerId.setEndDate(new Date());
        int sumDate = new Long(CalculationDate.calculationDate(rentByIdAndCustomerId.getBeginDate()
                , rentByIdAndCustomerId.getEndDate())).intValue();
        //若还车时间大于预定时间则需收取附加费
        /*if(sumDate<=rentByIdAndCustomerId.getRentSumDate()){
        }else {
        }*/
        rentByIdAndCustomerId.setRentSumDate(sumDate);
        //支付金额减去订金
        rentByIdAndCustomerId.setPaymentAmountRent(carInfoById.getRentPrice()
                .multiply(new BigDecimal(rentByIdAndCustomerId.getRentSumDate())));

        //更新租借支付状态
        rentByIdAndCustomerId.setPayRentStatus(PayRentStatusEnum.SUCCESS.getCode());
        //更新车辆状态
        carInfoById.setStatus(CarStatusEnum.RETURNED.getCode());
        Rent resultRent = rentRepository.save(rentByIdAndCustomerId);
        CarInfo resultCarInfo = carInfoRepository.save(carInfoById);
        if (resultRent == null) {
            log.error("【租借支付租金】支付失败，rent={}", rentDTO);
            throw new CarException(ResultEnum.RENT_UPDATE_FALL);
        }

        return Rent2RentDTOConverter.convert(resultRent);
    }

    @Override
    @Transactional
    public RentDTO finish(RentDTO rentDTO) throws Exception {

        if (rentDTO == null) {
            throw new CarException(ResultEnum.PARAM_ERROR);
        }

        //判断租借表是否有指定用户id对应rentId的信息，根据用户id跟rentId查找rent记录
        CarInfo carInfoById = carInfoRepository.findById(rentDTO.getCarId()).get();
        Rent rentByIdAndCustomerId = rentRepository.findByIdAndCustomerId(rentDTO.getId(), rentDTO.getCustomerId());
        if (rentByIdAndCustomerId == null) {
            log.error("【完结租借】更新失败，rentDTO={}", rentDTO);
            throw new CarException(ResultEnum.RENT_NOT_EXIST);
        }

        //判断租借状态
        if (rentByIdAndCustomerId.getStatus() != RentStatusEnum.SUBMIT.getCode()) {
            log.error("【完结租借】 租借状态错误，rentDTO={}", rentDTO);
            throw new CarException(ResultEnum.RENT_STATUS_ERROR);
        }
        //判断租借支付状态
        if (rentByIdAndCustomerId.getPayRentStatus() != PayRentStatusEnum.SUCCESS.getCode()) {
            log.error("【完结租借】 支付租金错误，rentDTO={}", rentDTO);
            throw new CarException(ResultEnum.PAY_RENT_ERROR);
        }
        //修改租借单状态
        rentByIdAndCustomerId.setStatus(RentStatusEnum.FINISH.getCode());
        //修改退还押金状态
        rentByIdAndCustomerId.setPayDepositStatus(PayDepositStatusEnum.REFUNDING.getCode());
        Rent resultRent = rentRepository.save(rentByIdAndCustomerId);

        if (resultRent == null) {
            log.error("【取消租借】更新失败，rent={}", rentDTO);
            throw new CarException(ResultEnum.RENT_UPDATE_FALL);
        }

        RentDTO resultRentDTO = new RentDTO();
        BeanUtils.copyProperties(resultRent, resultRentDTO);

        //退还用户押金（修改押金状态）
        RentDTO refundDeposit = refundDeposit(resultRentDTO);
        if (refundDeposit == null) {
            log.error("【押金退还】押金退还错误，rentDTO={}", resultRentDTO);
        }

        //更新车辆状态为未出租
        carInfoById.setStatus(CarStatusEnum.NOT_RENT.getCode());
        CarInfo resultCar = carInfoRepository.save(carInfoById);
        if (resultCar == null) {
            log.error("【取消租借】 车辆状态更新失败，rent={}", rentDTO);
            throw new CarException(ResultEnum.CAR_UPDATE);
        }


        return resultRentDTO;
    }

    @Override
    public RentDTO refundDeposit(RentDTO rentDTO) {

        Rent rent = rentRepository.findById(rentDTO.getId()).get();
        CarInfo carInfo = carInfoRepository.findById(rent.getCarId()).get();
        if (rent.getPayRentStatus() == PayRentStatusEnum.SUCCESS.getCode()
                && carInfo.getStatus() == CarStatusEnum.RETURNED.getCode()) {
            rent.setPayDepositStatus(PayDepositStatusEnum.REFUND_SUCCESSFULLY.getCode());
        } else {
            rent.setPayDepositStatus(PayDepositStatusEnum.PAID_FAIL.getCode());
        }

        Rent resultRent = rentRepository.save(rent);
        RentDTO resultRentDTO = new RentDTO();
        BeanUtils.copyProperties(resultRent, resultRentDTO);
        return resultRentDTO;
    }


    //管理员取消租借单
    @Override
    @Transactional
    public RentDTO cancel(RentDTO rentDTO) {
        if (rentDTO == null) {
            throw new CarException(ResultEnum.PARAM_ERROR);
        }

        //判断租借表是否有指定用户id对应rentId的信息，根据用户id跟rentId查找rent记录
        Rent rentByIdAndCustomerId = rentRepository.findByIdAndCustomerId(rentDTO.getId(), rentDTO.getCustomerId());
        if (rentByIdAndCustomerId == null) {
            log.error("【取消租借】取消失败，rentDTO={}", rentDTO);
            throw new CarException(ResultEnum.RENT_NOT_EXIST);
        }

        System.out.println("1111111"+rentByIdAndCustomerId);
        //判断租借状态
        if (!rentByIdAndCustomerId.getStatus().equals(RentStatusEnum.SUBMIT.getCode())) {
            log.error("【取消租借】 租借状态错误，rentId={},rentStatus={}", rentByIdAndCustomerId.getId(), rentByIdAndCustomerId.getStatus());
            throw new CarException(ResultEnum.RENT_STATUS_ERROR);
        }
        //判断押金是否支付,押金支付前才能退
        if (!rentByIdAndCustomerId.getPayDepositStatus().equals(PayDepositStatusEnum.WAIT.getCode())) {
            log.error("【取消租借】 押金状态错误，rent={}", rentByIdAndCustomerId.getId(), rentByIdAndCustomerId.getStatus());
            throw new CarException(ResultEnum.RENT_STATUS_ERROR);
        }
        //更新订单状态
        rentByIdAndCustomerId.setStatus(RentStatusEnum.CANCEL.getCode());
        //更新押金状态
        rentByIdAndCustomerId.setPayRentStatus(PayRentStatusEnum.PAID_FAIL.getCode());
        Rent updateResult = rentRepository.save(rentByIdAndCustomerId);
        if (updateResult == null) {
            log.error("【取消租借】更新失败，rent={}", rentDTO);
            throw new CarException(ResultEnum.RENT_UPDATE_FALL);
        }

        RentDTO refundDeposit = refundDeposit(rentDTO);
        if (refundDeposit == null) {
            log.error("【押金退还】押金退还错误，rentDTO={}", rentDTO);
        }

        CarInfo carInfo = carInfoRepository.findById(rentDTO.getCarId()).get();
        carInfo.setStatus(CarStatusEnum.NOT_RENT.getCode());
        CarInfo resultCar = carInfoRepository.save(carInfo);
        if (resultCar == null) {
            log.error("【取消租借】 车辆状态更新失败，rent={}", rentDTO);
            throw new CarException(ResultEnum.CAR_UPDATE);
        }

        RentDTO resultRentDTO = new RentDTO();
        BeanUtils.copyProperties(updateResult, resultRentDTO);
        return resultRentDTO;
    }

}
