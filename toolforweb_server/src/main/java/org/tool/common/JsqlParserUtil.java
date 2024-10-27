package org.tool.common;

import net.sf.jsqlparser.JSQLParserException;
import net.sf.jsqlparser.parser.CCJSqlParserUtil;
import net.sf.jsqlparser.statement.Statement;
import net.sf.jsqlparser.statement.select.Select;
import net.sf.jsqlparser.util.TablesNamesFinder;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class JsqlParserUtil {
    public static Map<String, ArrayList<String>> getTableNameAndAlias(String sql) throws JSQLParserException {
        Map<String, ArrayList<String>> result = new HashMap<>();

        Statement statement = CCJSqlParserUtil.parse(sql);
        if (statement instanceof Select) {
            Set<String> tableNames = TablesNamesFinder.findTables(sql);
        }

        return result;
    }

}
