package org.tool.common;

public class Constants {
    public class DBType {
        // Mysql
        public final static String MYSQL = "mysql";
        // postgres
        public final static String POSTGRES = "postgres";
        // oracle
        public final static String ORACLE = "oracle";
    }

    public class ErrorCode {
        // 系统错误
        public final static String SYSTEM_ERROR = "系统异常";
        // 实行错误
        public final static String RUNNING_ERROR = "运行异常";
    }

    public class ErrorMessage {
        // Json序列化错误
        public final static String JSON_SERIALIZATION_ERROR = "Json序列化失败";
        // 请输入数据库类型
        public final static String DBTYPE_INPUT_ERROR = "请输入数据库类型";
        // 数据库类型暂未录入
        public final static String DBTYPE_STATUS_ERROR = "数据库类型暂未录入";
        // 数据库驱动不存在
        public final static String DRIVER_EXISTS_ERROR = "数据库驱动不存在";
        // 数据库连接失败
        public final static String DB_CONNECT_ERROR = "数据库连接失败";
        // 暂时未实现该数据库的连接方式
        public final static String NO_DB_CONNECT_ERROR = "数据库连接失败";
    }

    public class SuccessMessage {
        // 数据库连接成功
        public final static String DB_CONNECT_SUCCESS = "数据库连接成功";
    }

    public class OtherConstants {
        // ResponseUtil.errorCode
        public final static String ERROR_CODE = "errorCode";
        // ResponseUtil.errorMessage
        public final static String ERROR_MESSAGE = "errorMessage";
        // ResponseUtil.data
        public final static String DATA = "data";
        // DBINFO
        public final static String DBINFO = "DBINFO";
    }
}
