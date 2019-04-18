package com.wp.flink;

import com.wp.stock.batch.entity.DailyTrading;
import com.wp.stock.batch.reduce.DailyTradingReduce;
import com.wp.stock.utils.HbaseUtils;
import com.wp.stock.utils.JDBCUtils;
import org.apache.flink.api.common.functions.FilterFunction;
import org.apache.flink.api.common.functions.MapFunction;
import org.apache.flink.api.java.DataSet;
import org.apache.flink.api.java.ExecutionEnvironment;
import org.apache.flink.api.java.aggregation.Aggregations;
import org.apache.flink.api.java.operators.DataSource;
import org.apache.flink.api.java.tuple.Tuple2;
import org.apache.flink.api.java.tuple.Tuple3;
import org.apache.flink.api.java.utils.ParameterTool;
import org.junit.Test;
import org.springframework.util.StringUtils;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

public class FlinkApp {


    @Test
    public void testGroup() throws Exception {
        ExecutionEnvironment env = ExecutionEnvironment.getExecutionEnvironment();

        String currentDate = "20190403";
        ArrayList<String> fiveDays = getRecentFiveDays(currentDate);

        DataSource<Tuple3<String, String, Float>> dailyTrading = env.readCsvFile("E:\\IDEA\\stockPortrait\\flinkTask\\src\\test\\resources\\daytrade.csv")
                .ignoreFirstLine()
                .includeFields(true, true, false, false, false, true, false, false, false, false, false)
                .types(String.class, String.class, float.class);

//        dailyTrading.filter(new FilterFunction<Tuple3<String, String, Float>>() {
//            @Override
//            public boolean filter(Tuple3<String, String, Float> value) throws Exception {
//                if (fiveDays.contains(value.f1)) {
//                    return true;
//                }
//                return false;
//            }
//        }).map(new MapFunction<Tuple3<String, String, Float>, Tuple3<String, String, Integer>>() {
//            @Override
//            public Tuple3<String, String, Integer> map(Tuple3<String, String, Float> value) throws Exception {
//                return new Tuple3<>(value.f0, value.f1, 1);
//            }
//        })
//                .groupBy(1)
//                .sum(2)
//                .print();

        dailyTrading.filter(new FilterFunction<Tuple3<String, String, Float>>() {
            @Override
            public boolean filter(Tuple3<String, String, Float> value) throws Exception {
                if (fiveDays.contains(value.f1)) {
                    return true;
                }
                return false;
            }
        }).map(new MapFunction<Tuple3<String, String, Float>, Tuple3<String, String, Integer>>() {
            @Override
            public Tuple3<String, String, Integer> map(Tuple3<String, String, Float> value) throws Exception {
                return new Tuple3<>(value.f0, value.f1, 1);
            }
        })
                .groupBy(0)
                .sum(2)
                .map(new MapFunction<Tuple3<String, String, Integer>, Tuple2<Integer, Integer>>() {
                    @Override
                    public Tuple2<Integer, Integer> map(Tuple3<String, String, Integer> value) throws Exception {
                        return new Tuple2<>(value.f2, 1);
                    }
                })
                .groupBy(0)
                .sum(1)
                .print();
    }

    @Test
    public void testTwoCompute() throws SQLException {
        ExecutionEnvironment env = ExecutionEnvironment.getExecutionEnvironment();
        String currentDate = "20190403";
        ArrayList<String> fiveDays = getRecentFiveDays(currentDate);

        DataSet<DailyTrading> dailyTrading = env.readCsvFile("E:\\IDEA\\stockPortrait\\flinkTask\\src\\test\\resources\\daytrade.csv")
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
                .reduceGroup(new DailyTradingReduce(currentDate));

//        String finalCurrentDate = currentDate;
//        DataSet<DailyTrading> reduceResult = dailyTrading.filter((FilterFunction<DailyTrading>) value -> {
//            if (finalCurrentDate.equals(value.getTradeDate())) {
//                return true;
//            }
//            return false;
//        });
//        System.out.println(reduceResult.count());

    }


    private ArrayList<String> getRecentFiveDays(String currentDate) throws SQLException {
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