package com.wp.stock.batch.reduce;

import com.wp.stock.batch.entity.DailyTrading;
import org.apache.flink.api.common.functions.GroupReduceFunction;
import org.apache.flink.util.Collector;
import org.springframework.util.StringUtils;

import java.util.Iterator;

public class DailyTradingReduce implements GroupReduceFunction<DailyTrading, DailyTrading> {

    private String currentDate;

    public DailyTradingReduce(String currentDate) {
        this.currentDate = currentDate;
    }

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
            if (dailyTrading.getTradeDate().equals(currentDate)) {
                result.setPrice(dailyTrading.getPrice());
            }
            sum += dailyTrading.getPrice();
            num++;
        }
        if (num == 5) {
            float ave = sum / num;

            // 计算并设置五日均值
            result.setAvePriceOfFiveDays((float) Math.round(ave * 100) / 100);

            // 计算price与均值的偏差
            float price = result.getPrice();
            float deviation = ((price - ave) / ave);
            result.setDeviationPrice((float) Math.round(deviation * 10000) / 10000);

            collector.collect(result);
        }
    }
}
