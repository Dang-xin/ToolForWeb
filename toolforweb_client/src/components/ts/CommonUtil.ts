import * as Constant from "./Constant";

export function isNotEmpty(str: string) {
    return str != null && str.replace(/[\n\r\s]+/g, Constant.String.Empty) !== Constant.String.Empty;
}

export function strToHtml(str: string) {
    return str.replace(/\n/g, "<br/>")
        .replace(/\s/g, "&nbsp;");
}