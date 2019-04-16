package com.wp.search.entity;

public class CellInfo{

    public String row;
    public String cf;
    public String qualifier;
    public String value;

    public CellInfo() {
    }

    public CellInfo(String row, String cf, String qualifier, String value) {
        this.row = row;
        this.cf = cf;
        this.qualifier = qualifier;
        this.value = value;
    }

    public String getRow() {
        return row;
    }

    public void setRow(String row) {
        this.row = row;
    }

    public String getCf() {
        return cf;
    }

    public void setCf(String cf) {
        this.cf = cf;
    }

    public String getQualifier() {
        return qualifier;
    }

    public void setQualifier(String qualifier) {
        this.qualifier = qualifier;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
