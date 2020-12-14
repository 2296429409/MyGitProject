package com.icis.controller;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.icis.pojo.Route;
import com.icis.pojo.RouteGetByIdPage;
import com.icis.pojo.indexRoute;
import com.icis.service.RouteService;
import com.icis.service.UserService;
import com.icis.service.impl.RouteServiceImpl;
import com.icis.service.impl.UserServiceImpl;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Map;

@WebServlet("/route/*")
public class RouteServlet extends BaseServlet {
    private ObjectMapper om=new ObjectMapper();
    private RouteService rs=new RouteServiceImpl();

    public void getRoutByCid(HttpServletRequest request, HttpServletResponse response){
        String cid = request.getParameter("cid");
        String page = request.getParameter("page");
        String search = request.getParameter("search");
        int i = Integer.parseInt(cid);
        if (page==null){
            page="1";
        }else if (page.equals("")){
            page="1";
        }

        RouteGetByIdPage routeGetByIdPage=rs.getRouteGetByIdPage(i,Integer.parseInt(page),search);
        String s = null;
        try {
            s = om.writeValueAsString(routeGetByIdPage);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        try {
            response.getWriter().write(s);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void getRouteByRid(HttpServletRequest request, HttpServletResponse response){
        String rid = request.getParameter("rid");
        Route routeById = rs.getRouteById(Integer.parseInt(rid));
        String s = null;
        try {
            s = om.writeValueAsString(routeById);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        try {
            response.getWriter().write(s);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public void getIndexRoute(HttpServletRequest request, HttpServletResponse response){
        indexRoute indexRoute= rs.getIndexRoute();
        String s = null;
        try {
            s = om.writeValueAsString(indexRoute);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        try {
            response.getWriter().write(s);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void getHotRoute(HttpServletRequest request, HttpServletResponse response){
        List<Route> routes= rs.getHotRoute();
        String s = null;
        try {
            s = om.writeValueAsString(routes);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        try {
            response.getWriter().write(s);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
