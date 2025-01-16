package org.tool.common;

import net.sf.jsqlparser.JSQLParserException;
import net.sf.jsqlparser.parser.CCJSqlParserUtil;
import net.sf.jsqlparser.statement.Statement;
import net.sf.jsqlparser.statement.select.PlainSelect;
import net.sf.jsqlparser.statement.select.Select;
import net.sf.jsqlparser.util.TablesNamesFinder;
import org.tool.bean.TableBean;
import org.tool.vistor.MySelectVisitor;
import org.tool.vistor.MyStatementVisor;

import java.sql.Connection;
import java.util.*;

public class JsqlParserUtil {
    private static Set<String> charRemoveList = new HashSet<>();
    private static DBUtil dbUtil = new DBUtil();

    static {
        charRemoveList.add("\\n");
        charRemoveList.add("\"");
    }

    public Set<TableBean> parserSql(Connection conn, String sql) throws ServerException {
        Set<TableBean> tableList;
        try {
            String formatSql = sqlFormat(sql);

            tableList = getTablesFromSql(conn, formatSql);
            Statement statement = CCJSqlParserUtil.parse(formatSql);
            statement.accept(new MyStatementVisor(), tableList);
        } catch (JSQLParserException JE) {
            throw new ServerException(Constants.ErrorCode.RUNNING_ERROR, Constants.ErrorMessage.PARSER_SQL_ERROR);
        }

        return tableList;
    }

    /**
     * 从sql中获取所有表名
     * @param conn
     * @param sql
     * @return
     * @throws ServerException
     */
    public Set<TableBean> getTablesFromSql(Connection conn, String sql) throws ServerException{
        Set<TableBean> tablesList = new HashSet<>();
        try {
            String formatSql = sqlFormat(sql);
            Set<String> tablesName = TablesNamesFinder.findTables(formatSql);

            for(String tableName : tablesName) {
                tablesList.add(dbUtil.getDBTables(conn, tableName).get(0));
            }
        } catch (JSQLParserException e) {
            throw new ServerException(Constants.ErrorCode.RUNNING_ERROR, Constants.ErrorMessage.GET_TABLES_NAME_FROM_SQL_ERROR);
        }

        return tablesList;
    }

    private String sqlFormat(String sql) {
        String formatSql = sql;
        for (String removedChar : charRemoveList) {
            formatSql = formatSql.replace(removedChar, " ");
        }
        return formatSql;
    }

}
