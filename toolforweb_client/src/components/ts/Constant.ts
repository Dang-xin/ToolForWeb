
export class response {
    public static readonly CODE:string = "Code";

    public static readonly DATA:string = "Data";
    // 成功信息 + 数据
    public static readonly RESPONSE_WITH_DATA:string = "ResponseWithData";
    // 请求成功
    public static readonly RESPONSE_WITHOUT_DATA:string = "ResponseWithoutData";
    // 系统错误
    public static readonly SYSTEM_ERROR:string = "SystemError";
    // 实行错误
    public static readonly RUNNING_ERROR:string = "RuntimeError";
    // 成功信息
    public static readonly SUCCESS_MESSAGE:string = "SuccessMessage";
    // 错误信息
    public static readonly ERROR_MESSAGE:string = "ErrorMessage";
}

export class DB {
    public static readonly Name:string = "ToolForWeb";
    public static readonly No:string = "No";
    public static readonly Version:string = "version";
}

export class StoreName {
    public static readonly DBQueryList = "queryListStore";
}

export class DBOperate {
    public static readonly Select = 0;
    public static readonly Insert = 1;
    public static readonly Delete = 2;
    public static readonly Update = 3;
}

export class String {
    public static readonly Empty = ""
}