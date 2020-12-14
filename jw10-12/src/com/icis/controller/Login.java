package com.icis.controller;

import com.icis.service.UserService;
import com.icis.service.impl.UserServiceImpl;
import com.icis.utils.GetService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/login")
public class Login extends HttpServlet {
    private UserService userService= (UserServiceImpl)GetService.getService("userService");


    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getSession().removeAttribute("msg");
        request.setCharacterEncoding("utf-8");
        response.setContentType("text/html;charset=utf-8");
        String user = request.getParameter("user");
        String password = request.getParameter("password");
        String code1 = request.getParameter("code");
        String code2 = (String) request.getSession().getAttribute("code");
        request.getSession().removeAttribute("code");
        if (code2.equals(code1)){
            int c=0;
            try {
                c=userService.UserLogin(user,password);
            } catch (Exception e) {
                e.printStackTrace();
            }
            if (c==0){
                request.setAttribute("msg","用户名或密码错误");
                request.getRequestDispatcher("/login.jsp").forward(request,response);
            }else {
                request.getSession().setAttribute("user",user);
                response.sendRedirect(request.getContextPath()+"/index.jsp");
            }
        }else{
            request.setAttribute("msg","验证码错误");
            request.getRequestDispatcher("/login.jsp").forward(request,response);
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doPost(request, response);
    }
}
