package com.wp.rpc;

import com.wp.service.HbaseServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("hbaseData")
public class HbaseDataControl {

    @Autowired
    private HbaseServiceImpl hbaseService;

    @RequestMapping(value = "monthkeyword",method = RequestMethod.POST)
    public String monthkeyword(String userid){
        String tablename = "userkeywordlabel";
        String rowkey=userid;
        String famliyname="baseinfo";
        String colum="month";
        String result = "";
        try {
            result = hbaseService.getdata(tablename,rowkey,famliyname,colum);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    @RequestMapping(value = "quarterkeyword",method = RequestMethod.POST)
    public String quarterkeyword(String userid){
        String tablename = "userkeywordlabel";
        String rowkey=userid;
        String famliyname="baseinfo";
        String colum="quarter";
        String result = "";
        try {
            result = hbaseService.getdata(tablename,rowkey,famliyname,colum);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

}
