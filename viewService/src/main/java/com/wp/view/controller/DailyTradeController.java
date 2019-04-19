package com.wp.view.controller;

import com.wp.common.constant.Constants;
import com.wp.common.constant.HbaseTableConstant;
import com.wp.common.dto.CellDto;
import com.wp.common.enums.ResultEnum;
import com.wp.common.vo.ResultVO;
import com.wp.view.client.HbaseDataClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("dailyTrade")
@Slf4j
public class DailyTradeController {

    @Resource
    private HbaseDataClient hbaseDataClient;

    @RequestMapping(value = "aveOfFiveDay", method = RequestMethod.GET)
    public ResultVO<List<CellDto>> aveOfFiveDay(String date) {

        log.info("date is {}", date);

        if (StringUtils.isEmpty(date)) {
            log.error("execute aveOfFiveDay failed, because {}", ResultEnum.PARAM_ERROR.getMessage());
            return ResultVO.error(ResultEnum.PARAM_ERROR);
        }
        ResultVO<List<CellDto>> resultVO = hbaseDataClient.getColumnsOfCf(date, HbaseTableConstant.FIVE_AVE_CF);
        List<CellDto> data = resultVO.getData();
        resultVO.add(Constants.LIST_NUMBER, data.size());
        return resultVO;
    }

    @RequestMapping(value = "deviationAveOfFiveDay", method = RequestMethod.GET)
    public ResultVO<List<CellDto>> deviationAveOfFiveDay(String date) {

        log.info("date is {}", date);

        if (StringUtils.isEmpty(date)) {
            log.error("execute deviationAveOfFiveDay failed, because {}", ResultEnum.PARAM_ERROR.getMessage());
            return ResultVO.error(ResultEnum.PARAM_ERROR);
        }
        ResultVO<List<CellDto>> resultVO = hbaseDataClient.getColumnsOfCf(date, HbaseTableConstant.DEVIATION_FIVE_AVE_CF);
        List data = resultVO.getData();
        resultVO.add(Constants.LIST_NUMBER, data.size());
        return resultVO;
    }
}
