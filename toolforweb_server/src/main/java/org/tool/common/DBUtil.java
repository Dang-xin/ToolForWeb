package org.tool.common;

import org.tool.bean.DBInfoBean;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBUtil {
    public static Connection conn;
    public static void getConnection(String DBType, DBInfoBean DBInfo) throws ServerException {
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

                conn = DriverManager.getConnection(DBConnectStr.toString(), DBInfo.getUserId(), DBInfo.getPassword());
            } catch (ClassNotFoundException e) {
                throw new ServerException(Constants.ErrorCode.SYSTEM_ERROR, Constants.ErrorMessage.DRIVER_EXISTS_ERROR);
            } catch (SQLException e) {
                throw new ServerException(Constants.ErrorCode.RUNNING_ERROR, Constants.ErrorMessage.DB_CONNECT_ERROR);
            }
        } else if (DBType.equals(Constants.DBType.ORACLE)) {

        } else {
            throw new ServerException(Constants.ErrorCode.RUNNING_ERROR, Constants.ErrorMessage.DBTYPE_STATUS_ERROR);
        }
    }
}
