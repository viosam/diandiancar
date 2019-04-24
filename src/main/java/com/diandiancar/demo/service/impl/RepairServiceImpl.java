package com.diandiancar.demo.service.impl;

import com.diandiancar.demo.converter.Repair2RepairDTOConverter;
import com.diandiancar.demo.dto.RepairDTO;
import com.diandiancar.demo.entity.CarInfo;
import com.diandiancar.demo.entity.Repair;
import com.diandiancar.demo.enums.CarStatusEnum;
import com.diandiancar.demo.enums.RepairStatusEnum;
import com.diandiancar.demo.enums.ResultEnum;
import com.diandiancar.demo.exception.CarException;
import com.diandiancar.demo.repository.CarInfoRepository;
import com.diandiancar.demo.repository.RepairRepository;
import com.diandiancar.demo.service.CarInfoService;
import com.diandiancar.demo.service.RepairService;
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

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;


@Service
@Slf4j
public class RepairServiceImpl implements RepairService {

    @Autowired
    RepairRepository repairRepository;

    @Autowired
    CarInfoService carInfoService;

    @Autowired
    CarInfoRepository carInfoRepository;


    @Override
    public Page<RepairDTO> findAll(Pageable pageable) {
        Page<Repair> repairPage = repairRepository.findAllByOrderByCreateTimeDesc(pageable);
        List<RepairDTO> repairDTOList = Repair2RepairDTOConverter.convert(repairPage.getContent());

        PageImpl<RepairDTO> repairDTOPage = new PageImpl<>(repairDTOList, pageable, repairPage.getTotalElements());
        return repairDTOPage;
    }

    @Override
    public Page<RepairDTO> findByCreateTimeBetween(String beginDate, String endDate, Pageable pageable) throws ParseException {

        Date begin = null;
        Date end = null;
        if (!StringUtils.isEmpty(beginDate)) {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            begin = sdf.parse(beginDate);
        }
        if (!StringUtils.isEmpty(endDate)) {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            end = sdf.parse(endDate);
        }
        Page<Repair> allByCreateTimeBetween = null;
        if (!StringUtils.isEmpty(beginDate) && !StringUtils.isEmpty(endDate)) {
            allByCreateTimeBetween = repairRepository.findAllByCreateTimeBetween(begin, end, pageable);
        } else if (!StringUtils.isEmpty(beginDate) && StringUtils.isEmpty(endDate)) {
            allByCreateTimeBetween = repairRepository.findAllByCreateTimeAfter(begin, pageable);
        } else if (StringUtils.isEmpty(beginDate) && !StringUtils.isEmpty(endDate)) {
            allByCreateTimeBetween = repairRepository.findAllByCreateTimeBefore(end, pageable);
        }
        List<RepairDTO> repairDTOList = Repair2RepairDTOConverter.convert(allByCreateTimeBetween.getContent());
        PageImpl<RepairDTO> repairDTOPage = new PageImpl<>(repairDTOList, pageable, allByCreateTimeBetween.getTotalElements());

        return repairDTOPage;


    }


    @Override
    public RepairDTO findOne(String repairId) {
        Repair repair = repairRepository.findById(repairId).get();
        if (repair == null) {
            throw new CarException(ResultEnum.REPAIR_NOT_EXIST);
        }
        RepairDTO repairDTO = new RepairDTO();
        BeanUtils.copyProperties(repair, repairDTO);

        return repairDTO;
    }

    @Override
    public RepairDTO create(String carId) {
        if (StringUtils.isEmpty(carId)) {
            throw new CarException(ResultEnum.PARAM_ERROR);
        }
        //判断车辆是否存在
        CarInfo carInfo = carInfoService.findById(carId);
        if (carInfo == null) {
            throw new CarException(ResultEnum.CAR_NOT_EXIST);
        }

        String repairId = KeyUtil.getUniqueKey();
        Repair repair = new Repair();
        repair.setId(repairId);
        repair.setCarId(carId);
        repair.setStatus(RepairStatusEnum.SUBMIT.getCode());

        Repair result = repairRepository.save(repair);
        //修改车辆状态
        carInfo.setStatus(CarStatusEnum.REPAIRING.getCode());
        CarInfo resultCarInfo = carInfoService.save(carInfo);
        if (resultCarInfo == null) {
            throw new CarException(ResultEnum.CAR_UPDATE);
        }

        RepairDTO resultRepairDTO = new RepairDTO();
        BeanUtils.copyProperties(result, resultRepairDTO);

        return resultRepairDTO;
    }

    @Override
    @Transactional
    public RepairDTO finish(RepairDTO repairDTO) {
        if (repairDTO == null) {
            throw new CarException(ResultEnum.PARAM_ERROR);
        }

        //查找repair记录
        Repair repairById = repairRepository.findById(repairDTO.getId()).get();
        if (repairById == null) {
            log.error("【完结维修】找不到该维修信息，repairDTO={}", repairDTO);
            throw new CarException(ResultEnum.REPAIR_NOT_EXIST);
        }

        //判断维修状态
        if (!repairById.getStatus().equals(RepairStatusEnum.SUBMIT.getCode())) {
            log.error("【完结维修】维修状态错误，repairDTO={}", repairDTO);
            throw new CarException(ResultEnum.REPAIR_STATUS_ERROR);
        }
        //更新预约状态
        repairById.setStatus(RepairStatusEnum.SUCCESS.getCode());

        //更新车辆状态
        Repair updateResult = repairRepository.save(repairById);
        CarInfo carInfo = carInfoService.findById(repairDTO.getCarId());
        carInfo.setStatus(CarStatusEnum.NOT_RENT.getCode());
        carInfoService.save(carInfo);
        if (updateResult == null) {
            log.error("【完结维修】更新失败，repairDTO={}", repairDTO);
            throw new CarException(ResultEnum.REPAIR_UPDAT_FAIL);
        }

        RepairDTO resultRepairDTO = new RepairDTO();
        BeanUtils.copyProperties(updateResult, resultRepairDTO);

        return resultRepairDTO;
    }

    @Override
    public RepairDTO approved(String repairId, String tag) {

        Repair repair = repairRepository.findById(repairId).get();
        if (repair == null) {
            throw new CarException(ResultEnum.REPAIR_NOT_EXIST);
        }
        if (!repair.getStatus().equals(RepairStatusEnum.SUBMIT.getCode())) {
            throw new CarException(ResultEnum.REPAIR_STATUS_ERROR);
        }
        if (tag.equals("1")) {
            repair.setStatus(RepairStatusEnum.SUCCESS.getCode());
        } else {
            repair.setStatus(RepairStatusEnum.FAIL.getCode());
        }
        Repair resultRepair = repairRepository.save(repair);

        //修改车辆状态
        CarInfo carInfo = carInfoService.findById(repair.getCarId());
        if (tag.equals("1")) {
            carInfo.setStatus(CarStatusEnum.REPAIRING.getCode());
            carInfoRepository.save(carInfo);
        }
        RepairDTO repairDTO = Repair2RepairDTOConverter.convert(resultRepair);
        return repairDTO;
    }
}
