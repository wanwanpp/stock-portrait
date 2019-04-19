package com.wp.search.repository;

import com.wp.search.po.Stock;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class StockRepositoryTest {

    @Autowired
    public StockListRepository stockListRepository;

    @Test
    public void getStockListAll() {
        List<Stock> stocks = stockListRepository.findAll();
        System.out.println(stocks);
    }

    @Test
    public void selectTscodeAndName() {
//        @Query("select s.tsCode, s.name from Stock s")
        // stockListRepository.selectTscodeAndName() 返回的是List<Object>
//        List<Stock> stocks = stockListRepository.selectTscodeAndName();
//        for (Stock stock: stocks){
//            // Ljava.lang.Object; cannot be cast to com.wp.search.po.Stock
//            System.out.println(stock.getName());
//        }
//        System.out.println(stocks);

//      @Query("select new Stock(s.tsCode, s.name) from Stock s")
//      期望的，返回List<Stock>
        List<Stock> stocks = stockListRepository.selectTscodeAndName();
        for (Stock stock : stocks) {
            System.out.println(stock.getName());
        }
        System.out.println(stocks);
    }
}