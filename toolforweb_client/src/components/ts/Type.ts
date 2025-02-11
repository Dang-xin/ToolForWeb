export type DBKey = {
    No: number;
    Status: number;
    [key: string]: any;
}

export type QueryTabItem = {
    No: number,
    Tab: string,
    Value: string,
}

export type QueryInfoItem = {
    No: number,                    // 唯一索引
    BusinessName: string,          // 机能名
    QueryName: string,             // 查询名
    Query: Array<QueryTabItem>,    // 查询内容
    Status: number,                // 状态 用于数据库操作
    Result: Array<object>          // 结果
}

export type SqlSelectItem = {
    No: number,
    Value: string,
    ValueToHtml: string
}