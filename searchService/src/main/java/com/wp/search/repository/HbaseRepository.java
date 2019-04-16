package com.wp.search.repository;

import com.wp.search.entity.CellInfo;

import java.util.List;

public interface HbaseRepository {

    /**
     * 查询出单表单row单cf下所有的column对应的CellInfo
     * @param tableName 表名
     * @param rowKey rowKey
     * @param cf  column family
     * @return
     */
    List<CellInfo> getCellInfoListOfCf(String tableName,String rowKey,String cf);
}
