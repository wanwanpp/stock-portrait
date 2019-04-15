package com.wp.service;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class HbaseServiceImplTest {

    public HbaseServiceImpl hbaseService;

    @Before
    public void before() {
        hbaseService = new HbaseServiceImpl();
    }

    @Test
    public void getColumnsOfCf() {
        hbaseService.getCellInfoListOfCf("test", "rowkey1", "info");
    }
}