package com.wp.stock.batch.entity;

public class DailyTrading {

    private String code;
    private Float price;
    private String tradeDate;
    private Float avePriceOfFiveDays;

    public String getTradeDate() {
        return tradeDate;
    }

    public void setTradeDate(String tradeDate) {
        this.tradeDate = tradeDate;
    }

    public Float getAvePriceOfFiveDays() {
        return avePriceOfFiveDays;
    }

    public void setAvePriceOfFiveDays(Float avePriceOfFiveDays) {
        this.avePriceOfFiveDays = avePriceOfFiveDays;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Float getPrice() {
        return price;
    }

    public void setPrice(Float price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return "DailyTrading{" +
                "code='" + code + '\'' +
                ", price=" + price +
                ", tradeDate='" + tradeDate + '\'' +
                ", avePriceOfFiveDays=" + avePriceOfFiveDays +
                '}';
    }
}
