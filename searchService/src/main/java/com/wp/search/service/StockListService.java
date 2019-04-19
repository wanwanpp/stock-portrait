package com.wp.search.service;

import com.wp.common.dto.StockDto;
import com.wp.search.po.Stock;
import com.wp.search.repository.StockListRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class StockListService {

    @Autowired
    private StockListRepository stockListRepository;

    public List<StockDto> selectTscodeAndName() {

        List<Stock> stocks = stockListRepository.selectTscodeAndName();
        List<StockDto> stockDtos = stocks.stream().map(new Function<Stock, StockDto>() {
            @Override
            public StockDto apply(Stock stock) {
                StockDto stockDto = new StockDto();
                BeanUtils.copyProperties(stock, stockDto);
                return stockDto;
            }
        }).collect(Collectors.toList());
        return stockDtos;
    }

    public StockDto selectByTsCode(String tsCode) {
        Stock stock = stockListRepository.findByTsCode(tsCode);
        StockDto stockDto = new StockDto();
        BeanUtils.copyProperties(stock,stockDto);
        return stockDto;
    }
}
