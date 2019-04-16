package com.wp.search.service;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class HbaseServiceImplTest {

    @Autowired
    public HbaseServiceImpl hbaseService;

    @Test
    public void getColumnsOfCf() {
        hbaseService.getCellInfoListOfCf("rowkey1");
    }
}