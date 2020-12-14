package com.icis.controller;

import com.icis.service.UserService;
import com.icis.service.impl.UserServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/activeUserServlet")
public class ActiveUserServlet extends HttpServlet {
    private UserService us=new UserServiceImpl();
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String code = request.getParameter("code");
        int i= us.setStatus(code);
        if (i==0){
            response.getWriter().write("激活无效!");
        }else {
            response.getWriter().write("激活成功!");
        }
        response.getWriter().write("返回<a href=\"http://192.168.4.31:8085/travel/login.html\">登录</a>页");
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doPost(request,response);
    }
}
