package com.icis.controller;

import com.icis.pojo.User;
import com.icis.service.UserService;
import com.icis.service.impl.UserServiceImpl;
import com.icis.utils.GetService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@WebServlet("/search")
public class SearchUser extends HttpServlet {
    private UserService userService= (UserServiceImpl)GetService.getService("userService");
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("utf-8");
        request.getSession().removeAttribute("users");
        String name = request.getParameter("name");
        String address = request.getParameter("address");
        String email = request.getParameter("email");
        String page = request.getParameter("page");
        if ("".equals(page)||page==null)
            page="1";
        if (name==""&&address==""&&email==""){
            response.sendRedirect(request.getContextPath()+"/userall");
        }else {
            List<User> users=userService.search(name,address,email,Integer.parseInt(page));
            request.getSession().setAttribute("users",users);
            response.sendRedirect(request.getContextPath()+"/list.jsp");
        }

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doPost(request, response);
    }
}
