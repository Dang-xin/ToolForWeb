
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