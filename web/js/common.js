// 全局变量_数据库表名
AllTablsName = [];

// 左填充
$.padLeft = function (str, char, len) {
    str = String(str);
    if (str.length < len) {
        let pad = new Array(len + 1).join(char);
        return pad.substring(0, pad.length - str.length) + str;
    } else {
        return str;
    }
}


