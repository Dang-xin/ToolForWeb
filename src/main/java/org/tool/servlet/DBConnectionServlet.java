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

import java.io.IOException;
import java.sql.Connection;

@WebServlet("/DBConnect")
public class DBConnectionServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request,response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        DBInfoBean dbInfo = new DBInfoBean();
        String dbType = request.getParameter("DBType");

        dbInfo.setServer(request.getParameter("Server"));
        dbInfo.setPort(request.getParameter("Port"));
        dbInfo.setDataBase(request.getParameter("DataBaseName"));
        dbInfo.setUserId(request.getParameter("UserID"));
        dbInfo.setPassword(request.getParameter("Password"));

        Connection conn = DBUtil.getConnection(dbType, dbInfo);

        if (conn != null) {
            response.getWriter().write(ResponseUtil.success(Constants.SuccessMessage.DB_CONNECT_SUCCESS));
        } else {
            response.getWriter().write((ResponseUtil.error(Constants.ErrorCode.SYSTEM_ERROR, Constants.ErrorMessage.NO_DB_CONNECT_ERROR)));
        }
    }

}
