package com.diandiancar.demo.service.impl;

import com.diandiancar.demo.dto.RepairDTO;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest
@RunWith(SpringRunner.class)
public class RepairServiceImplTest {

    @Autowired
    private RepairServiceImpl repairService;

    @Test
    public void create() {
        RepairDTO repairDTO = repairService.create("122");
        Assert.assertNotNull(repairDTO);
    }
}