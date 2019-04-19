package com.wp.view.service;

import com.wp.common.constant.HbaseTableConstant;
import com.wp.common.dto.CellDto;
import com.wp.common.dto.StockDto;
import com.wp.common.vo.ResultVO;
import com.wp.view.client.HbaseDataClient;
import com.wp.view.client.StockListDataClient;
import com.wp.view.dto.StockDetailDto;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class StockInfoService {

    @Resource
    private StockListDataClient stockListDataClient;

    @Resource
    private HbaseDataClient hbaseDataClient;

    public StockDetailDto getStockDetail(String tsCode, String date) {
        StockDetailDto stockDetailDto = new StockDetailDto();
        stockDetailDto.setTsCode(tsCode);
        ResultVO<StockDto> stockDtoResultVO = stockListDataClient.selectByTsCode(tsCode);
        if (ResultVO.isSuccess(stockDtoResultVO)) {
            stockDetailDto.setName(stockDtoResultVO.getData().getName());
        }

        ResultVO<CellDto> fiveAve = hbaseDataClient.getColumn(date, HbaseTableConstant.FIVE_AVE_CF, tsCode);
        if (ResultVO.isSuccess(fiveAve)){
            stockDetailDto.setAveOfFiveDay(Float.valueOf(fiveAve.getData().getValue()));
        }

        ResultVO<CellDto> deviationFiveAve = hbaseDataClient.getColumn(date, HbaseTableConstant.DEVIATION_FIVE_AVE_CF, tsCode);
        if (ResultVO.isSuccess(deviationFiveAve)){
            stockDetailDto.setDeviationAveOfFiveDay(Float.valueOf(deviationFiveAve.getData().getValue()));
        }
        return stockDetailDto;
    }
}
