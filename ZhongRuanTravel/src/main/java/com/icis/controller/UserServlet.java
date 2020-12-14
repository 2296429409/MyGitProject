package com.icis.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.icis.pojo.User;
import com.icis.service.RouteService;
import com.icis.service.UserService;
import com.icis.service.impl.RouteServiceImpl;
import com.icis.service.impl.UserServiceImpl;
import com.icis.utils.Md5Util;
import com.icis.utils.UuidUtil;
import org.apache.commons.beanutils.BeanUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.Map;

//@WebServlet("/user/*")
public class UserServlet extends BaseServlet {
    private ObjectMapper om=new ObjectMapper();
    private UserService us=new UserServiceImpl();

    public void activeUserServlet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String code = request.getParameter("code");
        int i= us.setStatus(code);
        if (i==0){
            response.getWriter().write("激活无效!");
        }else {
            response.getWriter().write("激活成功!");
        }
        response.getWriter().write("返回<a href=\"http://192.168.4.31:8085/travel/login.html\">登录</a>页");
    }

    public void getUserLogin(HttpServletRequest request, HttpServletResponse response) throws IOException {
        if (request.getParameter("i").equals("1")){
            String user =(String) request.getSession().getAttribute("user");
            if (user==null){
                user="";
            }
            response.getWriter().write(user);
        }else {
            request.getSession().removeAttribute("user");
            Cookie[] cookies = request.getCookies();
            if (cookies!=null){
                for (Cookie cookie : cookies) {
                    if (cookie.getName().equals("usercookie")){
                        System.out.println("退出登录");
                        cookie.setMaxAge(0);
                        response.addCookie(cookie);
                    }
                }
            }
        }
    }

    public void loginServlet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String check = request.getParameter("check");
        String checkcode_server = (String)request.getSession().getAttribute("CHECKCODE_SERVER");
        if (check.equalsIgnoreCase(checkcode_server)){
            Map<String, String[]> parameterMap = request.getParameterMap();
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
                    response.addCookie(c);
                    c.setPath("/");
                    System.out.println("记住登录");
                }
                request.getSession().setAttribute("user",user.getUsername());
                System.out.println("登录成功");
            }
        }else {
            response.getWriter().write("2");
        }
    }

    public void registerUser(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String check = request.getParameter("check");
        String checkcode_server = (String) request.getSession().getAttribute("CHECKCODE_SERVER");
        if (!check.equalsIgnoreCase(checkcode_server)){
            response.getWriter().write("1");
        }else {
            Map<String, String[]> parameterMap = request.getParameterMap();
            User user = new User();
            try {
                BeanUtils.populate(user,parameterMap);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }
            int i=us.checkUserName(user);
            if (i==0){
                user.setCode(UuidUtil.getUuid());
                try {
                    user.setPassword(Md5Util.encodeByMd5(user.getPassword()));
                } catch (Exception e) {
                    e.printStackTrace();
                }
                us.addUser(user);
            } else {
                response.getWriter().write("2");
            }
        }
    }
}
