import {keywords, operators, builtinFunctions, builtinVariables } from "@/components/ts/Sql";
import * as monaco from "monaco-editor";

export function main (functionName, editor) {
    let result;
    if (functionName === "getKeyWordsSuggestions") {
        result = getKeyWordsSuggestions();
    }
    return result;
}

const wordsWithTable = [
    "FROM",
    "JOIN"
]

let tableSuggestions = [];
let columnSuggestions = [];

function getKeyWordsSuggestions() {
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

function getOperatorsSuggestions() {
    let suggestions = [];
    operators.forEach(item => {
        suggestions.push({
            label: item,
            kind: monaco.languages.CompletionItemKind.Operator,
            insertText: item,
            detail: '内置操作符'
        });
    });
    return suggestions;
}

function getBuiltinFunctionsSuggestions() {
    let suggestions = [];
    builtinFunctions.forEach(item => {
        suggestions.push({
            label: item,
            kind: monaco.languages.CompletionItemKind.Function,
            insertText: item
        });
    });
    return suggestions;
}

function getBuiltinVariablesSuggestions() {
    let suggestions = [];
    builtinVariables.forEach(item => {
        suggestions.push({
            label: item,
            kind: monaco.languages.CompletionItemKind.Variable,
            insertText: item
        });
    });
    return suggestions;
}

function getAllTables(position) {

}

function getChangeText(position) {


}