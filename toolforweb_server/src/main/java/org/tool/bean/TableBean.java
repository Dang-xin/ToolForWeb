package org.tool.bean;

import java.util.ArrayList;

public class TableBean {
    // 表名
    private String tableName;
    // 表注释
    private String tableComment;
    // 表类型
    private String tableType;
    // 是否是DB元数据表
    private boolean isMetaTable;
    // 表别名
    private ArrayList<String> tableAlias;

    // 表字段
    private ArrayList<ColumnBean> columnList = new ArrayList<>();

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public String getTableComment() {
        return tableComment;
    }

    public void setTableComment(String tableComment) {
        this.tableComment = tableComment;
    }

    public String getTableType() {
        return tableType;
    }

    public void setTableType(String tableType) {
        this.tableType = tableType;
    }

    public ArrayList<ColumnBean> getColumnList() {
        return columnList;
    }

    public void setColumnList(ArrayList<ColumnBean> columnList) {
        this.columnList = columnList;
    }

    public boolean isMetaTable() {
        return isMetaTable;
    }

    public void setMetaTable(boolean metaTable) {
        isMetaTable = metaTable;
    }

    public ArrayList<String> getTableAlias() {
        return tableAlias;
    }

    public void setTableAlias(ArrayList<String> tableAlias) {
        this.tableAlias = tableAlias;
    }
}
