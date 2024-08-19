package org.tool.config;


import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebFilter(filterName = "CorsFilter")
public class CorsFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) {
    }

    @Override
    public void doFilter(ServletRequest httpRequest, ServletResponse httpResponse, FilterChain chain) throws IOException, ServletException {
        //变成http的
        HttpServletRequest request = (HttpServletRequest) httpRequest;
        HttpServletResponse response = (HttpServletResponse) httpResponse;
        // 添加参数，允许任意domain访问
        response.setContentType("text/html;charset=UTF-8");
        //禁用缓存，确保网页信息是最新数据
        response.setHeader("Pragma","No-cache");
        response.setHeader("Cache-Control","no-cache");
        response.setHeader("Access-Control-Allow-Origin", "http://localhost:5173");
        response.setHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS, HEAD, DELETE, PUT");
        response.setHeader("Access-Control-Max-Age", "3600");
        response.setHeader("Access-Control-Allow-Headers",
                "X-Requested-With, Content-Type, Authorization, Accept, Origin, User-Agent, Content-Range, Content-Disposition, Content-Description");
        response.setHeader("Access-Control-Allow-Credentials", "true");
        response.setContentType("application/json");
        chain.doFilter(request, response);
    }

    public void destroy() {}
}