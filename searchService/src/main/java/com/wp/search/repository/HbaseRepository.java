package com.wp.search.repository;

import com.wp.common.dto.CellDto;

import java.util.List;

public interface HbaseRepository {

    /**
     * 查询出单表单row单cf下所有的column对应的CellInfo
     * @param tableName 表名
     * @param rowKey rowKey
     * @param cf  column family
     * @return
     */
    List<CellDto> getCellDtoListOfCf(String tableName, String rowKey, String cf);
}
