package com.wp.stock.batch.reduce;

import com.wp.stock.batch.entity.DailyTrading;
import org.apache.flink.api.common.functions.GroupReduceFunction;
import org.apache.flink.util.Collector;
import org.springframework.util.StringUtils;

import java.util.Iterator;

public class DailyTradingReduce implements GroupReduceFunction<DailyTrading, DailyTrading> {

    @Override
    public void reduce(Iterable<DailyTrading> iterable, Collector<DailyTrading> collector) {

        Iterator<DailyTrading> iterator = iterable.iterator();
        DailyTrading result = new DailyTrading();
        float sum = 0.0f;
        int num = 0;
        while (iterator.hasNext()) {
            DailyTrading dailyTrading = iterator.next();
            if (StringUtils.isEmpty(result.getCode())) {
                result.setCode(dailyTrading.getCode());
            }
            sum += dailyTrading.getPrice();
            num++;
        }
        result.setAvePriceOfFiveDays(sum / num);
        if (num == 5)
            collector.collect(result);
    }
}
