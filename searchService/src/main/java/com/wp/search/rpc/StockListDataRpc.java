package com.wp.search.rpc;

import com.wp.common.dto.StockDto;
import com.wp.common.vo.ResultVO;
import com.wp.search.po.Stock;
import com.wp.search.service.StockListService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

@RestController
@RequestMapping("stockListData")
@Slf4j
public class StockListDataRpc {

    private final StockListService stockListService;

    @Autowired
    public StockListDataRpc(StockListService stockListService) {
        this.stockListService = stockListService;
    }

    @RequestMapping(value = "selectTscodeAndName", method = RequestMethod.GET)
    public ResultVO<List<StockDto>> selectTscodeAndName() {

        List<StockDto> stockDtos = stockListService.selectTscodeAndName();
        log.info("execute selectTscodeAndName succeed");

        return ResultVO.success(stockDtos);
    }

    @RequestMapping(value = "selectByTsCode", method = RequestMethod.GET)
    public ResultVO<StockDto> selectByTsCode(String tsCode) {
        log.info("tsCode is {}", tsCode);
        StockDto stockDto = stockListService.selectByTsCode(tsCode);

        log.info("execute selectByTsCode succeed");
        return ResultVO.success(stockDto);
    }
}
