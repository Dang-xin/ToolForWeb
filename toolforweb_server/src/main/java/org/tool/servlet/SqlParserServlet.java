package org.tool.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.tool.common.*;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

@WebServlet("/SqlParserServlet/*")
public class SqlParserServlet extends HttpServlet {
    private DBUtil dbUtil = new DBUtil();
    private JsqlParserUtil jsqlParserUtil = new JsqlParserUtil();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doPost(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {


        //获取URI路径
        String uri = request.getRequestURI();

        //把"/Module01/indexServlet/"删除，并追加”Method“
        String methodName = new StringBuffer(uri.substring(uri.lastIndexOf("/") + 1)).toString();

        //根据方法名获取对应的方法对象，最后执行方法
        Method method = null;
        try {
            //哪一个对象调用doGet方法，则this代表哪个对象
            method = this.getClass().getMethod(methodName, HttpServletRequest.class, HttpServletResponse.class);
            method.invoke(this, request, response);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            throw new RuntimeException(e);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    public void getAllDBTables(HttpServletRequest request, HttpServletResponse response) throws IOException {
        try (Connection conn = dbUtil.getConnection(request)) {
            dbUtil.getDBTables(conn, "%");
        } catch (ServerException SE) {
            response.getWriter().write(ResponseUtil.error(SE.getErrorCode(),SE.getErrorMessage()));
        } catch (SQLException SE)  {
            response.getWriter().write(ResponseUtil.error(Constants.ErrorCode.RUNNING_ERROR,Constants.ErrorMessage.DB_CONNECT_ERROR));
        }
    }

    public void parseSql(HttpServletRequest request, HttpServletResponse response) throws ServerException, IOException {
        String sql = Util.getDataFromHttp(request);
        ArrayList<HashMap<String,ArrayList<String>>> parseResult = new ArrayList<>();
        try (Connection conn = dbUtil.getConnection(request)) {
            jsqlParserUtil.parserSql(conn, sql);
        } catch (ServerException SE) {
            response.getWriter().write(ResponseUtil.error(SE.getErrorCode(),SE.getErrorMessage()));
        } catch (SQLException SE)  {
            response.getWriter().write(ResponseUtil.error(Constants.ErrorCode.RUNNING_ERROR,Constants.ErrorMessage.DB_CONNECT_ERROR));
        }
    }

}
