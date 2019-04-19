package com.wp.view.controller;

import com.wp.common.constant.Constants;
import com.wp.common.dto.StockDto;
import com.wp.common.enums.ResultEnum;
import com.wp.common.vo.ResultVO;
import com.wp.view.client.StockListDataClient;
import com.wp.view.dto.StockDetailDto;
import com.wp.view.service.StockInfoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("stockInfo")
@Slf4j
public class StockInfoController {

    @Resource
    private StockListDataClient stockListDataClient;

    @Resource
    private StockInfoService stockInfoService;

    @RequestMapping(value = "getTscodeAndName", method = RequestMethod.GET)
    public ResultVO<List<StockDto>> getTscodeAndName() {
        ResultVO<List<StockDto>> resultVO = stockListDataClient.selectTscodeAndName();
        List data = resultVO.getData();
        resultVO.add(Constants.LIST_NUMBER, data.size());
        return resultVO;
    }

    @RequestMapping(value = "stockDetail", method = RequestMethod.GET)
    public ResultVO<StockDetailDto> stockDetail(String tsCode, String date) {

        if (StringUtils.isEmpty(tsCode)) {
            log.error("execute stockDetail failed, because {}", ResultEnum.PARAM_ERROR.getMessage());
            return ResultVO.error(ResultEnum.PARAM_ERROR);
        }

        StockDetailDto stockDetail = stockInfoService.getStockDetail(tsCode, date);
        return ResultVO.success(stockDetail);
    }

}
