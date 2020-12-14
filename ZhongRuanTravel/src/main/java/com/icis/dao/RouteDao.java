package com.icis.dao;

import com.icis.pojo.Route;
import com.icis.pojo.RouteImg;
import com.icis.pojo.indexRoute;

import java.util.List;

public interface RouteDao {
    List<Route> getRoute();

    List<Route> getThemeRoute();

    List<Route> getNewRoute();

    List<Route> getRouteByCid(Integer cid,Integer page,String search);

    int getPageCountByCid(int i,String search);

    Route getRouteById(Integer rid);

    List<RouteImg> routeImgList(Integer rid);

    List<Route> Routeindex1();

    List<Route> Routeindex2();

    List<Route> getHotRoute();
}
