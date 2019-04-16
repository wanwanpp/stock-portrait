package com.wp.search.rpc;

import com.wp.common.enums.ResultEnum;
import com.wp.common.vo.ResultVO;
import com.wp.search.entity.CellInfo;
import com.wp.search.service.HbaseServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("hbaseData")
@Slf4j
public class HbaseDataRpc {

    @Autowired
    private HbaseServiceImpl hbaseService;

    /**
     * 根据日期获取当日的五日均价
     *
     * @param date 日期，格式为 yyyyMMdd
     * @return
     */
    @RequestMapping(value = "aveOfFiveDay", method = RequestMethod.GET)
    public ResultVO aveOfFiveDay(String date) {

        log.info("date is {}", date);

        if (StringUtils.isEmpty(date)) {
            log.error("execute aveOfFiveDay failed, because {}", ResultEnum.PARAM_ERROR.getMessage());
            return ResultVO.error(ResultEnum.PARAM_ERROR);
        }

        // 根据获取五日均价数据 （tableName,familyColumn,rowKey）-> colemns
        List<CellInfo> cellInfoList = hbaseService.getCellInfoListOfCf(date);
        return ResultVO.success(cellInfoList);
    }
}
