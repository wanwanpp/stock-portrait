# 数据
### 日交易数据
1. 放在**daytrade.csv**中
2. 通过tushre的pro.**daily**接口进行调用
3. 每天下午四点定时调用以获取当天的日交易数据，并追加到daytrade.csv文件中

### 交易日期数据
1. 用于判定哪些日期是交易日期，并与tushare平台保持一致
2. 通过tushare提供的pro.**trade_cal**接口进行调用
3. 存放在master主机的mysql中，数据库：**stock_portrait**，数据表：**trade_calendar**
4. 为及时同步接口提供的数据，需要每天重新获取交易日期数据（19年劳动节，五月一日为星期三，最初假日安排是两个周末加上星期三，后调整为政府网更新后的日期）

### 股票列表数据
1. 用于查询股票相关元信息（比如：根据股票代码查询股票名称）
2. 通过pro.**stock_basic**接口调用
3. 存放在master主机的mysql中，数据库：**stock_portrait**，数据表：**stock_basic**
4. 每日更新