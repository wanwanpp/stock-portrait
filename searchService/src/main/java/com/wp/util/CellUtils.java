package com.wp.util;

import org.apache.hadoop.hbase.Cell;
import org.apache.hadoop.hbase.CellUtil;
import org.apache.hadoop.hbase.util.Bytes;

public class CellUtils {

    public static CellInfo getCellInfo(Cell cell){
        CellInfo cellInfo = new CellInfo();
        cellInfo.setRow(getRow(cell));
        cellInfo.setCf(getFamily(cell));
        cellInfo.setQualifier(getQualifier(cell));
        cellInfo.setValue(getValue(cell));
        return cellInfo;
    }

    public static String getRow(Cell cell) {
        byte[] bytes = CellUtil.cloneRow(cell);
        return Bytes.toString(bytes);
    }

    public static String getQualifier(Cell cell) {
        byte[] bytes = CellUtil.cloneQualifier(cell);
        return Bytes.toString(bytes);
    }

    public static String getFamily(Cell cell) {
        byte[] bytes = CellUtil.cloneFamily(cell);
        return Bytes.toString(bytes);
    }

    public static String getValue(Cell cell) {
        byte[] bytes = CellUtil.cloneValue(cell);
        return Bytes.toString(bytes);
    }
}