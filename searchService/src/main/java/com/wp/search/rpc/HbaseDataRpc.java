package com.wp.search.rpc;

import com.wp.common.dto.CellDto;
import com.wp.common.enums.ResultEnum;
import com.wp.common.vo.ResultVO;
import com.wp.search.service.HbaseService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("hbaseData")
@Slf4j
public class HbaseDataRpc {

    @Autowired
    private HbaseService hbaseService;

    @RequestMapping(value = "getColumnsOfCf", method = RequestMethod.GET)
    public ResultVO<List<CellDto>> getColumnsOfCf(String rowkey, String cf) {

        if (StringUtils.isEmpty(rowkey) || StringUtils.isEmpty(cf)) {
            log.error("execute getColumnsOfCf failed, because {}", ResultEnum.PARAM_ERROR.getMessage());
            return ResultVO.error(ResultEnum.PARAM_ERROR);
        }

        List<CellDto> cellInfoList = hbaseService.getColumnsOfCf(rowkey, cf);
        return ResultVO.success(cellInfoList);
    }

    @RequestMapping(value = "getColumn", method = RequestMethod.GET)
    public ResultVO<CellDto> getColumn(String rowkey, String cf, String column) {

        if (StringUtils.isEmpty(rowkey) || StringUtils.isEmpty(cf)) {
            log.error("execute getColumn failed, because {}", ResultEnum.PARAM_ERROR.getMessage());
            return ResultVO.error(ResultEnum.PARAM_ERROR);
        }

        CellDto cellDto = hbaseService.getColumn(rowkey, cf, column);
        return ResultVO.success(cellDto);
    }
}
