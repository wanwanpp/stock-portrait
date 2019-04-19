package com.wp.view.dto;

public class StockDetailDto {

    private String tsCode;
    private String name;
    private Float aveOfFiveDay;
    private Float deviationAveOfFiveDay;


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTsCode() {
        return tsCode;
    }

    public void setTsCode(String tsCode) {
        this.tsCode = tsCode;
    }

    public Float getAveOfFiveDay() {
        return aveOfFiveDay;
    }

    public void setAveOfFiveDay(Float aveOfFiveDay) {
        this.aveOfFiveDay = aveOfFiveDay;
    }

    public Float getDeviationAveOfFiveDay() {
        return deviationAveOfFiveDay;
    }

    public void setDeviationAveOfFiveDay(Float deviationAveOfFiveDay) {
        this.deviationAveOfFiveDay = deviationAveOfFiveDay;
    }
}
