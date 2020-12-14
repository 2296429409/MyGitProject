package cn.smbms.interceptor;

import cn.smbms.pojo.MessageInfo;
import cn.smbms.pojo.User;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

// 拦截器
public class LoginInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String servletPath = request.getServletPath();
        System.out.println("servletPath:"+servletPath);
        if (servletPath.indexOf("/login.do") != -1){
            return true;
        }else {
            User loginer = (User) request.getSession().getAttribute("userSession");
            if (loginer == null || loginer.equals("") || loginer.getId() == null){
                response.sendRedirect("../error.jsp");
                return false;
            }else {
                return true;
            }
        }
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }
}
