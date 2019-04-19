package com.wp.view.client;

import com.wp.common.dto.CellDto;
import com.wp.common.vo.ResultVO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient(name = "SearchService")
public interface HbaseDataClient {

    @RequestMapping(value = "hbaseData/getColumnsOfCf", method = RequestMethod.GET)
    public ResultVO<List<CellDto>> getColumnsOfCf(@RequestParam("rowkey") String rowkey,
                                                  @RequestParam("cf") String cf);

    @RequestMapping(value = "hbaseData/getColumn", method = RequestMethod.GET)
    public ResultVO<CellDto> getColumn(@RequestParam("rowkey") String rowkey,
                                       @RequestParam("cf") String cf,
                                       @RequestParam("column") String column);

}
