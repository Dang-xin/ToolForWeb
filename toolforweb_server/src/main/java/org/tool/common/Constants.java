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

    public class SuccessCode {
        // 成功信息 + 数据
        public final static String RESPONSE_WITH_DATA = "ResponseWithData";
        // 成功信息
        public final static String RESPONSE_WITHOUT_DATA = "ResponseWithoutData";
    }
    public class ErrorCode {
        // 系统错误
        public final static String SYSTEM_ERROR = "SystemError";
        // 实行错误
        public final static String RUNNING_ERROR = "RuntimeError";
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
        public final static String NO_DB_CONNECT_ERROR = "暂时未实现该数据库的连接方式";
        // 从SQL中获取表名失败
        public final static String GET_TABLES_NAME_FROM_SQL_ERROR = "从SQL中获取表名失败";
        // SQL解析失败
        public final static String PARSER_SQL_ERROR = "SQL解析失败";
        // 获取请求参数失败
        public final static String GET_DATA_FROM_HTTP_ERROR = "获取请求参数失败";
        // 数据库操作失败
        public final static String DB_OPERATION_ERROR = "数据库操作失败";
    }

    public class SuccessMessage {
        // 数据库连接成功
        public final static String DB_CONNECT_SUCCESS = "数据库连接成功";
        // 从SQL中获取表名成功
        public final static String GET_TABLES_NAME_FROM_SQL_SUCCESS = "数据库连接成功";
    }

    public class OtherConstants {
        // ResponseUtil.errorCode
        public final static String CODE = "Code";
        // ResponseUtil.successMessage
        public final static String SUCCESS_MESSAGE = "SuccessMessage";
        // ResponseUtil.errorMessage
        public final static String ERROR_MESSAGE = "ErrorMessage";
        // ResponseUtil.data
        public final static String DATA = "Data";
        // DBINFO
        public final static String DBINFO = "DBINFO";
    }
}
