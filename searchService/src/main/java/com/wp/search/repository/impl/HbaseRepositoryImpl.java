package com.wp.search.repository.impl;

import com.wp.common.dto.CellDto;
import com.wp.search.repository.HbaseRepository;
import com.wp.search.util.CellUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.*;
import org.apache.hadoop.hbase.client.*;
import org.apache.hadoop.hbase.filter.CompareFilter;
import org.apache.hadoop.hbase.filter.RowFilter;
import org.apache.hadoop.hbase.filter.SubstringComparator;
import org.apache.hadoop.hbase.util.Bytes;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.NavigableMap;
import java.util.stream.Collectors;


@Repository
@Slf4j
public class HbaseRepositoryImpl implements HbaseRepository {

    private Admin admin = null;
    private Connection conn = null;

    @Value("${hbase.host}")
    public String masterHost;

    @PostConstruct
    public void init() {
        Configuration conf = HBaseConfiguration.create();
        conf.set("hbase.rootdir", "hdfs://" + masterHost + "/hbase");
        conf.set("hbase.zookeeper.quorum", masterHost);
        conf.set("hbase.client.scanner.timeout.period", "600000");
        conf.set("hbase.rpc.timeout", "600000");
        try {
            conn = ConnectionFactory.createConnection(conf);
            admin = conn.getAdmin();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<CellDto> getCellDtoListOfCf(String tableName, String rowKey, String cf) {

        List<CellDto> cellInfoList = new ArrayList<>();
        TableName name = TableName.valueOf(tableName);
        try {
            Table table = conn.getTable(name);
            Get get = new Get(rowKey.getBytes());
            get.addFamily(Bytes.toBytes(cf));
            Result result = table.get(get);

            List<Cell> cells = result.listCells();
            cellInfoList = cells.stream().map(cell -> CellUtils.getCellDto(cell)).collect(Collectors.toList());
        } catch (IOException e) {
            log.error("execute method getColumnsOfCf failed, tableName is {}, rowKey is {}, cf is {}", tableName, rowKey, cf);
            e.printStackTrace();
        }

        return cellInfoList;
    }

    /**
     * 查找单行单列族单列记录
     *
     * @param tableName
     * @param rowKey
     * @param cf
     * @param column
     * @return
     */
    public CellDto getValue(String tableName, String rowKey, String cf, String column) {
        TableName name = TableName.valueOf(tableName);
        CellDto cellDto = new CellDto();
        try {
            Table table = conn.getTable(name);
            Get g = new Get(rowKey.getBytes());
            g.addColumn(Bytes.toBytes(cf), Bytes.toBytes(column));
            Result rs = table.get(g);
            Cell cell = rs.rawCells()[0];
            cellDto = CellUtils.getCellDto(cell);
        } catch (IOException e) {
            log.error("execute method getValue failed, tableName is {}, rowKey is {}, cf is {}, column is {}", tableName, rowKey, cf, column);
            e.printStackTrace();
        }
        return cellDto;
    }

    /**
     * 创建表
     *
     * @param tableName    表名
     * @param columnFamily 列族（数组）
     */
    public void createTable(String tableName, String[] columnFamily) throws IOException {
        TableName name = TableName.valueOf(tableName);
        //如果存在则删除
        if (admin.tableExists(name)) {
            admin.disableTable(name);
            admin.deleteTable(name);
            System.out.println("create htable error! this table " + name + " already exists!");
        } else {
            HTableDescriptor desc = new HTableDescriptor(name);
            for (String cf : columnFamily) {
                desc.addFamily(new HColumnDescriptor(cf));
            }
            admin.createTable(desc);
        }
    }

    /**
     * 插入记录（单行单列族-多列多值）
     *
     * @param tableName     表名
     * @param row           行名
     * @param columnFamilys 列族名
     * @param columns       列名（数组）
     * @param values        值（数组）（且需要和列一一对应）
     */
    public void insertRecords(String tableName, String row, String columnFamilys, String[] columns, String[] values) throws IOException {
        TableName name = TableName.valueOf(tableName);
        Table table = conn.getTable(name);
        Put put = new Put(Bytes.toBytes(row));
        for (int i = 0; i < columns.length; i++) {
            put.addColumn(Bytes.toBytes(columnFamilys), Bytes.toBytes(columns[i]), Bytes.toBytes(values[i]));
            table.put(put);
        }
    }

    /**
     * 插入记录（单行单列族-单列单值）
     *
     * @param tableName    表名
     * @param row          行名
     * @param columnFamily 列族名
     * @param column       列名
     * @param value        值
     */
    public void insertOneRecord(String tableName, String row, String columnFamily, String column, String value) throws IOException {
        TableName name = TableName.valueOf(tableName);
        Table table = conn.getTable(name);
        Put put = new Put(Bytes.toBytes(row));
        put.addColumn(Bytes.toBytes(columnFamily), Bytes.toBytes(column), Bytes.toBytes(value));
        table.put(put);
    }

    /**
     * 删除一行记录
     *
     * @param tablename 表名
     * @param rowkey    行名
     */
    public void deleteRow(String tablename, String rowkey) throws IOException {
        TableName name = TableName.valueOf(tablename);
        Table table = conn.getTable(name);
        Delete d = new Delete(rowkey.getBytes());
        table.delete(d);
    }

    /**
     * 删除单行单列族记录
     *
     * @param tablename    表名
     * @param rowkey       行名
     * @param columnFamily 列族名
     */
    public void deleteColumnFamily(String tablename, String rowkey, String columnFamily) throws IOException {
        TableName name = TableName.valueOf(tablename);
        Table table = conn.getTable(name);
        Delete d = new Delete(rowkey.getBytes()).deleteFamily(Bytes.toBytes(columnFamily));
        table.delete(d);
    }

    /**
     * 删除单行单列族单列记录
     *
     * @param tablename    表名
     * @param rowkey       行名
     * @param columnFamily 列族名
     * @param column       列名
     */
    public void deleteColumn(String tablename, String rowkey, String columnFamily, String column) throws IOException {
        TableName name = TableName.valueOf(tablename);
        Table table = conn.getTable(name);
        Delete d = new Delete(rowkey.getBytes()).deleteColumn(Bytes.toBytes(columnFamily), Bytes.toBytes(column));
        table.delete(d);
    }


    /**
     * 查找一行记录
     *
     * @param tablename 表名
     * @param rowKey    行名
     */
    public String selectRow(String tablename, String rowKey) throws IOException {
        String record = "";
        TableName name = TableName.valueOf(tablename);
        Table table = conn.getTable(name);
        Get g = new Get(rowKey.getBytes());
        Result rs = table.get(g);
        NavigableMap<byte[], NavigableMap<byte[], NavigableMap<Long, byte[]>>> map = rs.getMap();
        for (Cell cell : rs.rawCells()) {
            StringBuffer stringBuffer = new StringBuffer().append(Bytes.toString(cell.getRow())).append("\t")
                    .append(Bytes.toString(cell.getFamily())).append("\t")
                    .append(Bytes.toString(cell.getQualifier())).append("\t")
                    .append(Bytes.toString(cell.getValue())).append("\n");
            String str = stringBuffer.toString();
            record += str;
        }
        return record;
    }

    /**
     * 查询表中所有行（Scan方式）
     *
     * @param tablename
     * @return
     */
    public String scanAllRecord(String tablename) throws IOException {
        String record = "";
        TableName name = TableName.valueOf(tablename);
        Table table = conn.getTable(name);
        Scan scan = new Scan();
        ResultScanner scanner = table.getScanner(scan);
        try {
            for (Result result : scanner) {
                for (Cell cell : result.rawCells()) {
                    StringBuffer stringBuffer = new StringBuffer().append(Bytes.toString(cell.getRow())).append("\t")
                            .append(Bytes.toString(cell.getFamily())).append("\t")
                            .append(Bytes.toString(cell.getQualifier())).append("\t")
                            .append(Bytes.toString(cell.getValue())).append("\n");
                    String str = stringBuffer.toString();
                    record += str;
                }
            }
        } finally {
            if (scanner != null) {
                scanner.close();
            }
        }

        return record;
    }

    /**
     * 根据rowkey关键字查询报告记录
     *
     * @param tablename
     * @param rowKeyword
     * @return
     */
    public List scanReportDataByRowKeyword(String tablename, String rowKeyword) throws IOException {
        ArrayList<Object> list = new ArrayList<>();

        Table table = conn.getTable(TableName.valueOf(tablename));
        Scan scan = new Scan();

        //添加行键过滤器，根据关键字匹配
        RowFilter rowFilter = new RowFilter(CompareFilter.CompareOp.EQUAL, new SubstringComparator(rowKeyword));
        scan.setFilter(rowFilter);

        ResultScanner scanner = table.getScanner(scan);
        try {
            for (Result result : scanner) {
//                list.add(result.getValue());
            }
        } finally {
            if (scanner != null) {
                scanner.close();
            }
        }

        return list;
    }

    /**
     * 根据rowkey关键字和时间戳范围查询报告记录
     *
     * @param tablename
     * @param rowKeyword
     * @return
     */
    public List scanReportDataByRowKeywordTimestamp(String tablename, String rowKeyword, Long minStamp, Long maxStamp) throws IOException {
        ArrayList<Object> list = new ArrayList<>();

        Table table = conn.getTable(TableName.valueOf(tablename));
        Scan scan = new Scan();
        //添加scan的时间范围
        scan.setTimeRange(minStamp, maxStamp);

        RowFilter rowFilter = new RowFilter(CompareFilter.CompareOp.EQUAL, new SubstringComparator(rowKeyword));
        scan.setFilter(rowFilter);

        ResultScanner scanner = table.getScanner(scan);
        try {
            for (Result result : scanner) {
                list.add(null);
            }
        } finally {
            if (scanner != null) {
                scanner.close();
            }
        }

        return list;
    }


    /**
     * 删除表操作
     *
     * @param tablename
     */
    public void deleteTable(String tablename) throws IOException {
        TableName name = TableName.valueOf(tablename);
        if (admin.tableExists(name)) {
            admin.disableTable(name);
            admin.deleteTable(name);
        }
    }
}
