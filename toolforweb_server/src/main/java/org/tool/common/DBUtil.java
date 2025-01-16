package org.tool.common;

import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import org.tool.bean.ColumnBean;
import org.tool.bean.DBInfoBean;
import org.tool.bean.TableBean;

import java.sql.*;
import java.util.ArrayList;

public class DBUtil {
    public Connection getConnection(String DBType, DBInfoBean DBInfo) throws ServerException {
        if (DBType == null || DBType.isEmpty()) {
            throw new ServerException(Constants.ErrorCode.RUNNING_ERROR, Constants.ErrorMessage.DBTYPE_INPUT_ERROR);
        }

        if (DBType.equals(Constants.DBType.MYSQL)) {

        } else if (DBType.equals(Constants.DBType.POSTGRES)) {
            try {
                StringBuffer DBConnectStr = new StringBuffer("jdbc:postgresql://");
                Class.forName("org.postgresql.Driver");

                DBConnectStr.append(DBInfo.getServer());
                DBConnectStr.append(":");
                DBConnectStr.append(DBInfo.getPort());
                DBConnectStr.append("/");
                DBConnectStr.append(DBInfo.getDataBase());

                return DriverManager.getConnection(DBConnectStr.toString(), DBInfo.getUserId(), DBInfo.getPassword());
            } catch (ClassNotFoundException e) {
                throw new ServerException(Constants.ErrorCode.SYSTEM_ERROR, Constants.ErrorMessage.DRIVER_EXISTS_ERROR);
            } catch (SQLException e) {
                throw new ServerException(Constants.ErrorCode.RUNNING_ERROR, Constants.ErrorMessage.DB_CONNECT_ERROR);
            }
        } else if (DBType.equals(Constants.DBType.ORACLE)) {

        } else {
            throw new ServerException(Constants.ErrorCode.RUNNING_ERROR, Constants.ErrorMessage.DBTYPE_STATUS_ERROR);
        }
        return null;
    }

    public Connection getConnection(HttpServletRequest request) throws ServerException {
        String dbType = (String) request.getSession().getAttribute("DBType");
        DBInfoBean dbInfo = (DBInfoBean) request.getSession().getAttribute("DBConnectionInfo");

        if (Util.isNullOrEmpty(dbType) || dbInfo == null) {
            return null;
        }

        return this.getConnection(dbType, dbInfo);
    }

    public ArrayList<TableBean> getDBTables(Connection conn, String tableNamePattern) throws ServerException {
        ArrayList<TableBean> tableList = new ArrayList<>();
        try {
            DatabaseMetaData metaData = conn.getMetaData();
            // 获取表名
            ResultSet tables = metaData.getTables(null, null, tableNamePattern, new String[]{"TABLE"});
            while (tables.next()) {
                TableBean table = new TableBean();
                table.setTableName(tables.getString("TABLE_NAME"));
                table.setTableType(tables.getString("TABLE_NAME"));
                table.setTableComment(tables.getString("REMARKS"));
                table.setMetaTable(true);

                // 获取列信息
                ResultSet columns = metaData.getColumns(null, null, table.getTableName(), null);
                while (columns.next()) {
                    ColumnBean column = new ColumnBean();
                    column.setColumnName(columns.getString("COLUMN_NAME"));
                    column.setColumnType(columns.getString("TYPE_NAME"));
                    column.setColumnComment(columns.getString("REMARKS"));
                    column.setColumnSize(columns.getInt("COLUMN_SIZE"));
                    column.setNotNull(columns.getString("NULLABLE") == "YES" ? false : true);
                    table.getColumnList().add(column);
                }

                // 获取主键信息
                ResultSet primaryKeys = metaData.getPrimaryKeys(null, null, table.getTableName());
                while (primaryKeys.next()) {
                    for (ColumnBean column : table.getColumnList()) {
                        if (column.getColumnName().equals(primaryKeys.getString("COLUMN_NAME"))) {
                            column.setPrimaryKey(true);
                            break;
                        }
                    }
                }
                tableList.add(table);
            }
        } catch (SQLException e) {
            throw new ServerException(Constants.ErrorCode.RUNNING_ERROR, Constants.ErrorMessage.DB_OPERATION_ERROR);
        }
        return tableList;
    }
}
