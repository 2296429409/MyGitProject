package com.icis.filters;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.icis.pojo.User;
import com.icis.service.UserService;
import com.icis.service.impl.UserServiceImpl;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

//@WebFilter("/login.html")
public class LoginFilter implements Filter {
    private UserService us;
    private ObjectMapper om;
    public void destroy() {

    }

    public void doFilter(ServletRequest req, ServletResponse rep, FilterChain chain) throws ServletException, IOException {
        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) rep;

        Cookie[] cookies1 = request.getCookies();
        if (cookies1!=null){
            for (Cookie cookie : cookies1) {
                System.out.println(cookie.getName()+"---"+cookie.getValue());
            }
        }else{
            System.out.println("cookie is null");
        }

        if (request.getSession().getAttribute("user")==null){
            Cookie[] cookies = request.getCookies();
            if (cookies!=null){
                for (Cookie cookie : cookies) {
                    if (cookie.getName().equals("usercookie")){
                        User user = om.readValue(cookie.getValue(), User.class);
                        int i = us.loginUser(user);
                        if (i!=0){
                            request.getSession().setAttribute("user",user.getUsername());
//                            response.sendRedirect(request.getContextPath()+"/index.html");
                            break;
                        }else {
//                            cookie.setMaxAge(0);
//                            response.addCookie(cookie);
                        }
                    }
                }
            }
        }
        chain.doFilter(req, rep);
    }

    public void init(FilterConfig config) throws ServletException {
        us =new UserServiceImpl();
        om=new ObjectMapper();
    }

}
