package com.icis.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.icis.pojo.User;
import com.icis.service.UserService;
import com.icis.service.impl.UserServiceImpl;
import com.icis.utils.Md5Util;
import org.apache.commons.beanutils.BeanUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.Map;

@WebServlet("/loginServlet")
public class LoginServlet extends HttpServlet {
    private UserService us=new UserServiceImpl();
    private ObjectMapper om=new ObjectMapper();
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String check = request.getParameter("check");
        String checkcode_server = (String)request.getSession().getAttribute("CHECKCODE_SERVER");
        if (check.equalsIgnoreCase(checkcode_server)){
            Map<String, String[]> parameterMap = request.getParameterMap();
//            for (String s : parameterMap.keySet()) {
//                System.out.println(s+"---"+parameterMap.get(s)[0]);
//            }
            User user = new User();
            User useryuan = new User();
            try {
                BeanUtils.populate(user,parameterMap);
                useryuan=user;
                user.setPassword(Md5Util.encodeByMd5(user.getPassword()));
            }catch (Exception e) {
                e.printStackTrace();
            }
            int i=us.loginUser(user);
            if (i==0){
                response.getWriter().write("1");
            }else {
                if (request.getParameter("login")!=null){
                    Cookie c = new Cookie("usercookie",om.writeValueAsString(useryuan));
                    c.setMaxAge(60*60*24*30);
                    c.setPath("/");
                    response.addCookie(c);
                    System.out.println("记住登录");
                }
                request.getSession().setAttribute("user",user.getUsername());
                System.out.println("登录成功");
            }
        }else {
            response.getWriter().write("2");
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doPost(request, response);
    }
}
