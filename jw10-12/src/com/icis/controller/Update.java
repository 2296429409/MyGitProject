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
import java.util.Map;

@WebServlet("/update")
public class Update extends HttpServlet {
    private UserService userService= (UserServiceImpl)GetService.getService("userService");
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("utf-8");
        Map<String, String[]> map = request.getParameterMap();
        User user = new User(Integer.parseInt(request.getParameter("id")),map.get("name")[0],map.get("sex")[0],Integer.parseInt(map.get("age")[0]),map.get("address")[0],map.get("qq")[0],map.get("email")[0]);
        System.out.println(user);
        int i=userService.update(user);
        response.sendRedirect(request.getContextPath()+"/userall");
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doPost(request, response);
    }
}
