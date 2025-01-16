package org.tool.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.tool.bean.DBInfoBean;
import org.tool.common.Constants;
import org.tool.common.DBUtil;
import org.tool.common.ResponseUtil;
import org.tool.common.ServerException;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

@WebServlet("/DBConnect")
public class DBConnectionServlet extends HttpServlet {

    private DBUtil dbUtil = new DBUtil();
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request,response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        DBInfoBean dbInfo = new DBInfoBean();
        String dbType = request.getParameter("DBType");

        dbInfo.setServer(request.getParameter("server"));
        dbInfo.setPort(request.getParameter("port"));
        dbInfo.setDataBase(request.getParameter("dataBase"));
        dbInfo.setUserId(request.getParameter("userID"));
        dbInfo.setPassword(request.getParameter("password"));

        try {
            Connection conn = dbUtil.getConnection(dbType, dbInfo);
            if (conn == null) {
                response.getWriter().write(ResponseUtil.error(Constants.ErrorCode.RUNNING_ERROR, Constants.ErrorMessage.DB_CONNECT_ERROR));
            }
            conn.close();
            request.getSession().setAttribute("DBType", dbType);
            request.getSession().setAttribute("DBConnectionInfo", dbInfo);
            response.getWriter().write(ResponseUtil.success(Constants.SuccessMessage.DB_CONNECT_SUCCESS));
        } catch (ServerException SE) {
            response.getWriter().write(ResponseUtil.error(SE.getErrorCode(),SE.getErrorMessage()));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}
