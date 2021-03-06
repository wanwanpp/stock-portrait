package com.wp.stock.utils;

import com.wp.stock.batch.entity.DailyTrading;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.*;
import org.apache.hadoop.hbase.util.Bytes;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class HbaseUtils {
    private static Admin admin = null;
    private static Connection conn = null;

    private static String MASTER_HOST = "172.23.253.80";

    static {
        // 创建hbase配置对象
        Configuration conf = HBaseConfiguration.create();
        conf.set("hbase.rootdir", "hdfs://" + MASTER_HOST + "/hbase");
        //使用eclipse时必须添加这个，否则无法定位
        conf.set("hbase.zookeeper.quorum", MASTER_HOST);
        conf.set("hbase.client.scanner.timeout.period", "600000");
        conf.set("hbase.rpc.timeout", "600000");
        try {
            conn = ConnectionFactory.createConnection(conf);
            // 得到管理程序
            admin = conn.getAdmin();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public static void list() {
        try {
            TableName[] tableNames = admin.listTableNames();
            for (TableName tableName : tableNames) {
                System.out.println(tableName.getNameAsString());
            }
        } catch (IOException e) {
            System.out.println("list table names fail");
        }
    }

    /**
     * 插入多条数据，create "userflaginfo,"baseinfo"
     * create "tfidfdata,"baseinfo"
     */
    public static void put(String tablename, String rowkey, String famliyname, Map<String, String> datamap) throws Exception {
        Table table = conn.getTable(TableName.valueOf(tablename));
        // 将字符串转换成byte[]
        byte[] rowkeybyte = Bytes.toBytes(rowkey);
        Put put = new Put(rowkeybyte);
        if (datamap != null) {
            Set<Map.Entry<String, String>> set = datamap.entrySet();
            for (Map.Entry<String, String> entry : set) {
                String key = entry.getKey();
                Object value = entry.getValue();
                put.addColumn(Bytes.toBytes(famliyname), Bytes.toBytes(key), Bytes.toBytes(value + ""));
            }
        }
        table.put(put);
        table.close();
        System.out.println("ok");
    }

    /**
     *
     */
    public static String getdata(String tablename, String rowkey, String famliyname, String colum) throws Exception {

        Table table = conn.getTable(TableName.valueOf(tablename));
        // 将字符串转换成byte[]
        byte[] rowkeybyte = Bytes.toBytes(rowkey);
        Get get = new Get(rowkeybyte);
        Result result = table.get(get);
        byte[] resultbytes = result.getValue(famliyname.getBytes(), colum.getBytes());
        if (resultbytes == null) {
            return null;
        }

        return new String(resultbytes);
    }

    /**
     * 插入有单列单条数据
     */
    public static void putdata(String tablename, String rowkey, String famliyname, String colum, String data) throws Exception {
        Table table = conn.getTable(TableName.valueOf(tablename));
        Put put = new Put(rowkey.getBytes());
        put.addColumn(famliyname.getBytes(), colum.getBytes(), data.getBytes());
        table.put(put);
    }

    /**
     * 插入有多列单条数据
     */
    public static void putdataWithMuchColumns(String tablename, String rowkey, String famliyname, HashMap<String, String> column2Data) throws Exception {
        Table table = conn.getTable(TableName.valueOf(tablename));
        Put put = new Put(rowkey.getBytes());
        for (HashMap.Entry<String, String> entry : column2Data.entrySet()) {
            put.addColumn(famliyname.getBytes(), entry.getKey().getBytes(), entry.getValue().getBytes());
        }
        table.put(put);
    }
}
