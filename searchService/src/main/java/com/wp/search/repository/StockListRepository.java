package com.wp.search.repository;

import com.wp.search.po.Stock;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface StockListRepository extends JpaRepository<Stock, Integer> {

    //    @Query("select s.tsCode, s.name from Stock s")  //返回的是List<Object>
//    @Query(value = "select ts_code, name from stock_list", nativeQuery = true)  // 测试报错
//    @Query(value = "select * from stock_list", nativeQuery = true)  // 测试不报错
    @Query("select new Stock(s.tsCode, s.name) from Stock s")   //期望的，返回List<Stock>
    List<Stock> selectTscodeAndName();

    Stock findByTsCode(String tsCode);
}
