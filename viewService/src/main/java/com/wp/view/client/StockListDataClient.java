package com.wp.view.client;

import com.wp.common.dto.StockDto;
import com.wp.common.vo.ResultVO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient(name = "SearchService")
public interface StockListDataClient {

    @RequestMapping(value = "stockListData/selectTscodeAndName",method = RequestMethod.GET)
    public ResultVO<List<StockDto>> selectTscodeAndName();

    @RequestMapping(value = "stockListData/selectByTsCode",method = RequestMethod.GET)
    public ResultVO<StockDto> selectByTsCode(@RequestParam("tsCode") String tsCode);
}
