package com.wp.rpc;

import com.wp.service.HbaseServiceImpl;
import com.wp.util.CellInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("hbaseData")
public class HbaseDataControl {

    @Autowired
    private HbaseServiceImpl hbaseService;

    @RequestMapping("aveOfFiveDay")
    public List<CellInfo> aveOfFiveDay() {
        // TODO: 2019/4/15 根据获取五日均价数据 （tableName,familyColumn,rowKey）-> colemns

        List<CellInfo> cellInfoList = hbaseService.getCellInfoListOfCf("test", "rowkey1", "info");
        return cellInfoList;
    }

    @RequestMapping(value = "monthkeyword", method = RequestMethod.POST)
    public String monthkeyword(String userid) {
        String tablename = "userkeywordlabel";
        String rowkey = userid;
        String famliyname = "baseinfo";
        String colum = "month";
        String result = "";
        try {
            result = hbaseService.getdata(tablename, rowkey, famliyname, colum);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

}
