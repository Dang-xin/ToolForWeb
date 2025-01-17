
export enum response {
    CODE = "Code",

    DATA = "Data",
    // 成功信息 + 数据
    RESPONSE_WITH_DATA = "ResponseWithData",
    // 请求成功
    RESPONSE_WITHOUT_DATA = "ResponseWithoutData",
    // 系统错误
    SYSTEM_ERROR = "SystemError",
    // 实行错误
    RUNNING_ERROR = "RuntimeError",
    // 成功信息
    SUCCESS_MESSAGE = "SuccessMessage",
    // 错误信息
    ERROR_MESSAGE = "ErrorMessage"
}

export enum DB {
    Name = "ToolForWeb",
    ID = "id",
    Status = "status"
}

export enum StoreName {
    DBQueryList = "queryListStore"
}

export enum DBOperate {
    Select = 0,
    Insert = 1,
    Delete = 2,
    Update = 3
}

export enum queryItem {
    Number = "number",
    Tab = "Tab",
    Value = "value",
    ValueToHtml = "valueToHtml"
}

export enum String {
    Empty = ""
}

export enum QueryInfoItem {
    ID = DB.ID,                    // 唯一索引
    BusinessName = "businessName", // 机能名
    QueryName = "queryName",       // 查询名
    Query = "query",               // 查询内容
    Status = DB.Status,            // 状态 用于数据库操作
    Result = "result"              // 结果
}