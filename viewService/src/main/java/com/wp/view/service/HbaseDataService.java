package com.wp.view.service;

import com.wp.common.vo.ResultVO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "SearchService")
public interface HbaseDataService {

    @RequestMapping(value = "hbaseData/aveOfFiveDay",method = RequestMethod.GET)
    public ResultVO aveOfFiveDay(@RequestParam("date") String date);

}
