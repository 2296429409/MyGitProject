package com.icis.service;

import com.icis.pojo.Route;
import com.icis.pojo.RouteGetByIdPage;
import com.icis.pojo.RouteImg;
import com.icis.pojo.indexRoute;

import java.util.List;

public interface RouteService {


    List<Route> getRouteByCid(Integer cid,Integer page,String search);

    int getPageCountByCid(int i,String search);

    Route getRouteById(Integer rid);

    List<RouteImg> routeImgList(Integer rid);

    RouteGetByIdPage getRouteGetByIdPage(int i, int parseInt, String search);


    indexRoute getIndexRoute();

    List<Route> getHotRoute();
}
