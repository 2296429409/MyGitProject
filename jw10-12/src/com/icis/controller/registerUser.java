package com.icis.controller;

import com.icis.service.UserService;
import com.icis.service.impl.UserServiceImpl;
import com.icis.pojo.Login;
import com.icis.utils.GetService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/register")
public class registerUser extends HttpServlet {
    private UserService userService=(UserServiceImpl)GetService.getService("userService");
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
            if(!("".equals(user)&&"".equals(password))){
                Login login = new Login(user,password,null);
                try {
                    userService.AddLogin(login);
                } catch (Exception e) {
                    System.out.println("添加失败");
                    request.setAttribute("msg","用户名重复");
                    request.getRequestDispatcher("/register.jsp").forward(request,response);
                }
                request.getRequestDispatcher("/login.jsp").forward(request,response);
            }else {
                request.setAttribute("msg","请输入用户名或密码");
                request.getRequestDispatcher("/register.jsp").forward(request,response);
            }
        }else{
            request.setAttribute("msg","验证码错误");
            request.getRequestDispatcher("/register.jsp").forward(request,response);
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doPost(request, response);
    }
}
