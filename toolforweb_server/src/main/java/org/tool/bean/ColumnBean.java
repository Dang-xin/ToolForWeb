package org.tool.bean;

import java.util.ArrayList;

public class ColumnBean {
    // 字段名
    private String columnName;
    // 字段注释
    private String columnComment;
    // 字段类型
    private String columnType;
    // 字段长度
    private int columnSize;
    // 是否是主键
    private boolean isPrimaryKey;
    // 是否非空
    private boolean isNotNull;
    // 字段别名
    private ArrayList<String> columnAlias;

    public String getColumnName() {
        return columnName;
    }

    public void setColumnName(String columnName) {
        this.columnName = columnName;
    }

    public String getColumnComment() {
        return columnComment;
    }

    public void setColumnComment(String columnComment) {
        this.columnComment = columnComment;
    }

    public String getColumnType() {
        return columnType;
    }

    public void setColumnType(String columnType) {
        this.columnType = columnType;
    }

    public int getColumnSize() {
        return columnSize;
    }

    public void setColumnSize(int columnSize) {
        this.columnSize = columnSize;
    }

    public boolean isPrimaryKey() {
        return isPrimaryKey;
    }

    public void setPrimaryKey(boolean primaryKey) {
        isPrimaryKey = primaryKey;
    }

    public boolean isNotNull() {
        return isNotNull;
    }

    public void setNotNull(boolean notNull) {
        isNotNull = notNull;
    }

    public ArrayList<String> getColumnAlias() {
        return columnAlias;
    }

    public void setColumnAlias(ArrayList<String> columnAlias) {
        this.columnAlias = columnAlias;
    }
}
