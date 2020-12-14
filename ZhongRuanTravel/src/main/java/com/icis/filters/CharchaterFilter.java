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

/**
 * 解决全站乱码问题，处理所有的请求
 */
@WebFilter("/*")
public class CharchaterFilter implements Filter {
    private UserService us;
    private ObjectMapper om;
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        us =new UserServiceImpl();
        om=new ObjectMapper();
    }

    @Override
    public void doFilter(ServletRequest req, ServletResponse rep, FilterChain filterChain) throws IOException, ServletException {
        //将父接口转为子接口
        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) rep;
        //获取请求方法
        String method = request.getMethod();
        //解决post请求中文数据乱码问题
        if(method.equalsIgnoreCase("post")){
            request.setCharacterEncoding("utf-8");
        }
        //处理响应乱码
        response.setContentType("text/html;charset=utf-8");

//        Cookie[] cookies1 = request.getCookies();
//        if (cookies1!=null){
//            int i=0;
//            for (Cookie cookie : cookies1) {
//                i++;
//                System.out.println(i+cookie.getName()+"---"+cookie.getValue());
//
//            }
//        }else{
//            System.out.println("cookie is null");
//        }


        if (request.getSession().getAttribute("user")==null){
            Cookie[] cookies = request.getCookies();
            if (cookies!=null){
                for (Cookie cookie : cookies) {
                    if (cookie.getName().equals("usercookie")){
                        User user = om.readValue(cookie.getValue(), User.class);
                        int i = us.loginUser(user);
                        if (i!=0){
                            request.getSession().setAttribute("user",user.getUsername());
                            response.sendRedirect(request.getContextPath()+"/index.html");
                            break;
                        }else {
//                            cookie.setMaxAge(0);
//                            response.addCookie(cookie);
                        }
                    }
                }
            }
        }



        filterChain.doFilter(request,response);
    }

    @Override
    public void destroy() {

    }
}
