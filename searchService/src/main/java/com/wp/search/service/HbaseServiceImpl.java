package com.wp.search.service;

import com.wp.search.entity.CellInfo;
import com.wp.search.entity.HbaseTableInfo;
import com.wp.search.repository.impl.HbaseRepositoryImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class HbaseServiceImpl {

    private final HbaseTableInfo hbaseTableInfo;

    @Autowired
    public HbaseServiceImpl(HbaseTableInfo hbaseTableInfo) {
        this.hbaseTableInfo = hbaseTableInfo;
    }

    @Autowired
    private HbaseRepositoryImpl hbaseRepository;


    public List<CellInfo> getCellInfoListOfCf(String rowKey) {
        List<CellInfo> cellInfos = hbaseRepository.getCellInfoListOfCf(this.hbaseTableInfo.getTableName(), rowKey, this.hbaseTableInfo.getCfOfFiveAve());
        log.info("execute method getCellInfoListOfCf with rowKye {} succeed", rowKey);
        return cellInfos;
    }
}
