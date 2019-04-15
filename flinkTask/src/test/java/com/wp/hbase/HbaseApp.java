package com.wp.hbase;

import org.junit.Test;

import java.util.Date;

public class HbaseApp {

    @Test
    public void listTableNames(){
        HbaseUtils.list();
    }

    @Test
    public void putData() throws Exception {
        HbaseUtils.putdata("test","rowkey1","info","age", String.valueOf(13));
    }

    @Test
    public void getData() throws Exception{
        System.out.println(new Date());
        String data = HbaseUtils.getdata("test", "rowkey1", "info", "age");
        System.out.println(new Date());
        System.out.println(data);
    }
}
