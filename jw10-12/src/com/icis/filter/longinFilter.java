package com.icis.filter;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

//@WebFilter("/*")
public class longinFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;

        String requestURI = request.getRequestURI();
        if (requestURI.contains("/css/")||requestURI.contains("/js/")||requestURI.contains("/checkcode")||requestURI.contains("/login")){
            filterChain.doFilter(servletRequest,servletResponse);
        }else {
            String usernull = request.getParameter("user");
            if ("usernull".equals(usernull)){
                request.getSession().removeAttribute("user");
            }
            String user = (String) request.getSession().getAttribute("user");
            if (user!=null){
                filterChain.doFilter(servletRequest,servletResponse);
            }else {
                request.setAttribute("msg","请先登录");
                request.getRequestDispatcher("/login.jsp").forward(servletRequest,servletResponse);
            }
        }
    }

    @Override
    public void destroy() {

    }
}
