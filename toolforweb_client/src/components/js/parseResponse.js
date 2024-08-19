import { ElMessage } from 'element-plus'

// 用于判断返回值是否
function parseResponse (response) {
    if (response.data === "") {
        ElMessage({
            message: '请求返回的结果异常，需要联系管理员!',
            type: "error"
        });
    } else if (response.data["errorCode"] !== undefined) {
        ElMessage({
            message: response.data["errorCode"] + ':' + response.data["errorMessage"],
            type: "error"
        });
    } else if (response.data["data"] === undefined) {
        ElMessage({
            message: response.data,
            type: "success"
        });
    } else {
        return response.data;
    }
}

export { parseResponse };