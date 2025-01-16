import {keywords, operators, builtinFunctions, builtinVariables } from "./Sql";
import * as monaco from "monaco-editor";
import request from "./AxiosRequest";
import {parseResponse} from "./ParseResponse";
import * as Constant from "./Constant";
import * as Util from "./CommonUtil";

const wordsWithTable = [
    "FROM",
    "JOIN"
]

let tableSuggestions = [];
let columnSuggestions = [];

export function getKeyWordsSuggestions() {
    let suggestions = [];
    keywords.forEach(item => {
        suggestions.push({
            label: item,
            kind: monaco.languages.CompletionItemKind.Keyword,
            insertText: item,
            detail: '内置关键字'
        });
    });
    return suggestions;
}

export function getAllTables(sql:  string) {
    if (!Util.isNotEmpty(sql)) {
        return;
    }
    request({
        url: "/SqlParserServlet/getAllDBTables",
        method: "post",
        data: JSON.stringify(sql)
    }).then((res) => {
        parseResponse(res);
    });
}

export function parseSql(sql:  string) {
    if (!Util.isNotEmpty(sql)) {
        return;
    }
    request({
        url: "/SqlParserServlet/parseSql",
        method: "post",
        data: JSON.stringify(sql)
    }).then((res) => {
        parseResponse(res);
    });
}