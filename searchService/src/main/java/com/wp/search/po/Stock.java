package com.wp.search.po;

import javax.persistence.*;

@Entity
@Table(name = "stock_list")
public class Stock {

    @Id
    @GeneratedValue
    private Integer id;

    @Column(name = "ts_code")
    private String tsCode;

    @Column
    private String symbol;

    @Column
    private String name;

    @Column
    private String area;

    @Column
    private String industry;

    @Column
    private String market;

    @Column(name = "list_date")
    private String listDate;

    public Stock() {
    }

    public Stock(String tsCode, String name) {
        this.tsCode = tsCode;
        this.name = name;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTsCode() {
        return tsCode;
    }

    public void setTsCode(String tsCode) {
        this.tsCode = tsCode;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getIndustry() {
        return industry;
    }

    public void setIndustry(String industry) {
        this.industry = industry;
    }

    public String getMarket() {
        return market;
    }

    public void setMarket(String market) {
        this.market = market;
    }

    public String getListDate() {
        return listDate;
    }

    public void setListDate(String listDate) {
        this.listDate = listDate;
    }
}
