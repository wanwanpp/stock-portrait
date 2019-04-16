package com.wp.view.controller;

import com.wp.common.enums.ResultEnum;
import com.wp.common.vo.ResultVO;
import com.wp.view.service.HbaseDataService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@RequestMapping("hbaseData")
@Slf4j
public class HbaseDataViewController {

    @Resource
    private HbaseDataService hbaseDataService;

    @RequestMapping(value = "aveOfFiveDay", method = RequestMethod.GET)
    public ResultVO aveOfFiveDay(String date) {

        log.info("date is {}", date);

        if (StringUtils.isEmpty(date)) {
            log.error("execute aveOfFiveDay failed, because {}", ResultEnum.PARAM_ERROR.getMessage());
            return ResultVO.error(ResultEnum.PARAM_ERROR);
        }

        return hbaseDataService.aveOfFiveDay(date);
    }
}
