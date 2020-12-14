package com.icis.controller;

import com.icis.pojo.TbAddress;
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
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.List;

@WebServlet("/userall")
public class GetUser extends HttpServlet {
    private UserService userService=(UserServiceImpl)GetService.getService("userService");
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        request.setCharacterEncoding("utf-8");
        response.setContentType("text/html;charset=utf-8");
        request.getSession().removeAttribute("users");
        String name = request.getParameter("name");
        String address = request.getParameter("address");
        String email = request.getParameter("email");
        String page = request.getParameter("page");
        if ("".equals(page)||page==null)
            page="1";
        if (name==null)
            name="";
        if (address==null)
            address="";
        if (email==null)
            email="";
        List<User> users=userService.search(name,address,email,Integer.parseInt(page));
        request.getSession().setAttribute("users",users);
        Integer i=userService.getI(name,address,email);


        List<TbAddress> addresses=userService.getAddresses();
        request.getSession().setAttribute("addresses",addresses);
        response.sendRedirect(request.getContextPath()+"/list.jsp?i="+i+"&page="+page+"&name="+URLEncoder.encode(name,"UTF-8")+"&address="+URLEncoder.encode(address,"UTF-8")+"&email="+URLEncoder.encode(email,"UTF-8"));
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doPost(request, response);
    }
}
