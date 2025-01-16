import { ElMessage } from 'element-plus'
import * as Constant from "./Constant";

// 用于判断返回值是否
export function parseResponse(response: Object) {
    let data = response["data"];
    if (typeof response === "string" && data === Constant.String.Empty) {
        ElMessage({
            message: '请求返回的结果异常，需要联系管理员!',
            type: "error"
        });
    } else if (data[Constant.response.CODE] === Constant.response.RESPONSE_WITH_DATA) {
        ElMessage({
            message: data[Constant.response.SUCCESS_MESSAGE],
            type: "success"
        });
    } else if (data[Constant.response.CODE] === Constant.response.RESPONSE_WITHOUT_DATA) {
        ElMessage({
            message: data[Constant.response.SUCCESS_MESSAGE],
            type: "success"
        });
    } else if (data[Constant.response.CODE] === Constant.response.SYSTEM_ERROR) {
        ElMessage({
            message: "系统错误: " + data[Constant.response.ERROR_MESSAGE],
            type: "error"
        });
    } else if (data[Constant.response.CODE] === Constant.response.RUNNING_ERROR) {
        ElMessage({
            message: "运行错误: " + data[Constant.response.ERROR_MESSAGE],
            type: "error"
        });
    }
    return data;
}

export function error(message: string) {
    ElMessage({
        message: message,
        type: "error"
    });
}

export function success(message: string) {
    ElMessage({
        message: message,
        type: "success"
    });
}

export function info(message: string) {
    ElMessage({
        message: message,
        type: "info"
    });
}