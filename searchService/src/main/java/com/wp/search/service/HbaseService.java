package com.wp.search.service;

import com.wp.common.dto.CellDto;
import com.wp.search.repository.impl.HbaseRepositoryImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.wp.common.constant.HbaseTableConstant.DAILY_TRADE_TABLE_NAME;

@Service
@Slf4j
public class HbaseService {

    @Autowired
    private HbaseRepositoryImpl hbaseRepository;

    public List<CellDto> getColumnsOfCf(String rowKey, String cf) {
        List<CellDto> cellDtos = hbaseRepository.getCellDtoListOfCf(DAILY_TRADE_TABLE_NAME, rowKey, cf);
        log.info("execute method getAveOfFiveDay with rowKye {} and cf {} succeed", rowKey, cf);
        return cellDtos;
    }

    public CellDto getColumn(String rowKey, String cf, String column) {
        CellDto cellDto = hbaseRepository.getValue(DAILY_TRADE_TABLE_NAME, rowKey, cf, column);
        log.info("execute method getColumn with rowKye {}, cf {}, column {} succeed", rowKey, cf, column);
        return cellDto;
    }
}
