package com.wp.stock.batch.task;

import com.wp.stock.batch.entity.DailyTrading;
import com.wp.stock.batch.reduce.DailyTradingReduce;
import com.wp.stock.utils.HbaseUtils;
import com.wp.stock.utils.JDBCUtils;
import org.apache.flink.api.common.functions.FilterFunction;
import org.apache.flink.api.java.DataSet;
import org.apache.flink.api.java.ExecutionEnvironment;
import org.apache.flink.api.java.utils.ParameterTool;
import org.springframework.util.StringUtils;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

public class AvePriceOfFiveDaysTask {

    public static void main(String[] args) throws Exception {
        ParameterTool params = ParameterTool.fromArgs(args);
        ExecutionEnvironment env = ExecutionEnvironment.getExecutionEnvironment();
        env.getConfig().setGlobalJobParameters(params);

        // 确定当前日期
        String currentDate = "20190403";
        if (!StringUtils.isEmpty(currentDate)) {

        } else if (params.has("date")) {
            currentDate = params.get("input");
        } else {
            System.out.println("Use --date to specify date to compute.");
            currentDate = String.valueOf(new SimpleDateFormat("yyyyMMdd").format(new Date()));
        }

        ArrayList<String> fiveDays = getRecentFiveDays(currentDate);

        DataSet<DailyTrading> dailyTrading = env.readCsvFile("E:\\IDEA\\stockPortrait\\flinkTask\\src\\test\\resources\\daytrade.csv")
//        DataSet<DailyTrading> dailyTrading = env.readCsvFile("hdfs://master:8020/share_data/daytrade.csv")
                .includeFields(true, true, false, false, false, true, false, false, false, false, false)
                .pojoType(DailyTrading.class, "code", "tradeDate", "price");

        DataSet<DailyTrading> reduceResult = dailyTrading.filter(new FilterFunction<DailyTrading>() {
            @Override
            public boolean filter(DailyTrading value) throws Exception {
                if (fiveDays.contains(value.getTradeDate())) {
                    return true;
                }
                return false;
            }
        })
                .groupBy("code")
                .reduceGroup(new DailyTradingReduce());

//        String finalCurrentDate = currentDate;
//        DataSet<DailyTrading> reduceResult = dailyTrading.filter((FilterFunction<DailyTrading>) value -> {
//            if (finalCurrentDate.equals(value.getTradeDate())) {
//                return true;
//            }
//            return false;
//        });
//        System.out.println(reduceResult.count());

//        3580
//        3566
//        3547

        List<DailyTrading> results = reduceResult.collect();
        String tablename = "dailyTradeInfo";
        String rowkey = currentDate;
        String famliyname = "aveInfo";
        HashMap<String, String> colunm2Data = new HashMap<>(4000);
        for (DailyTrading trading : results) {
            colunm2Data.put(trading.getCode(), String.valueOf(trading.getAvePriceOfFiveDays()));
        }
        HbaseUtils.putdataWithMuchColumns(tablename, rowkey, famliyname, colunm2Data);
    }

    private static ArrayList<String> getRecentFiveDays(String currentDate) throws SQLException {
        ArrayList<String> dateList = new ArrayList<>(5);
        currentDate = "'" + currentDate + "'";
        ResultSet rs = JDBCUtils.getResultSetWithPreparedStatement("SELECT cal_date FROM trade_calendar where cal_date < " + currentDate + " and is_open=1 " +
                "order by cal_date desc limit 5");
        while (rs.next()) {
            String date = rs.getString(1);
            dateList.add(date);
        }
        return dateList;
    }
}
