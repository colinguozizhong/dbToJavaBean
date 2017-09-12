mysql,sqlserver,Oracle 数据库生成javabean小工具
http://www.oschina.net/code/snippet_128886_26726
参考了：http://marsvaadin.iteye.com/blog/1465592  相关内容。

Oracle中查看所有表和字段
http://www.cnblogs.com/emanlee/archive/2011/12/02/2272629.html

三大数据库连接字符串：
/*
 * MYSQL
 * dataSource.driverClassName=com.mysql.jdbc.Driver
 * dataSource.url=jdbc:mysql://192.168.12.55/fzrating?characterEncoding=utf8
 * dataSource.username= fzratingtest
 * dataSource.password= fzratingtest
 * {userpwd=fzratingtest, dbName=fzrating, JdbcURL=jdbc:mysql://, catName=f:\, userName=fzratingtest, driverClassName=com.mysql.jdbc.Driver, driver=com.mysql.jdbc.Driver, dbStr=/, showTable=select table_name from information_schema.tables where table_schema = '%', packName=davaDemo, showColumns=show columns from %, jdbc=jdbc:mysql://192.168.12.55:3306/fzrating}
*/
		
/*
 * SQlServer
 * dataSource.driverClassName=net.sourceforge.jtds.jdbc.Driver
 * dataSource.url=jdbc:jtds:sqlserver://192.168.12.54:1433/FZ_DEV
 * dataSource.username= fz_dev
 * dataSource.password= fz_dev
 * {userpwd=fz_dev, dbName=fz_dev, JdbcURL=jdbc:sqlserver://, catName=f:\, userName=fz_dev, driverClassName=com.microsoft.sqlserver.jdbc.SQLServerDriver, driver=com.microsoft.sqlserver.jdbc.SQLServerDriver, dbStr=;databaseName=, showTable=select Name from sysobjects where xtype='u' and status>=0, packName=javaDemo, showColumns=select column_name,data_type from information_schema.columns where table_name = '%', jdbc=jdbc:sqlserver://192.168.12.54:1433;databaseName=fz_dev}
*/

/* Oracle
 * dataSourceforSe.driverClassName=oracle.jdbc.driver.OracleDriver
 * dataSourceforSe.url=jdbc:oracle:thin:@192.168.12.230:1521:demo230
 * dataSourceforSe.username=ccxe
 * dataSourceforSe.password=ccxe
 * {userpwd=ccxe, dbName=demo230, JdbcURL=jdbc:oracle:thin:@, catName=f:\, userName=ccxe, driverClassName=oracle.jdbc.driver.OracleDriver, driver=oracle.jdbc.driver.OracleDriver, dbStr=:, showTable=SELECT TABLE_NAME FROM USER_TABLES, packName=javaDemo, showColumns=select table_name from dba_tables where owner='CCXE', jdbc=jdbc:oracle:thin:@192.168.12.230:1521:demo230}
*/	