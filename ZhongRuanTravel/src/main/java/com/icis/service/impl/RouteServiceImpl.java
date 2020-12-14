package com.icis.service.impl;

import com.icis.dao.RouteDao;
import com.icis.dao.SellerDao;
import com.icis.dao.impl.RouteDaoImpl;
import com.icis.dao.impl.SellerDaoImpl;
import com.icis.pojo.*;
import com.icis.service.RouteService;

import java.util.List;

public class RouteServiceImpl implements RouteService {
    private RouteDao rd=new RouteDaoImpl();
    private SellerDao sd=new SellerDaoImpl();



    @Override
    public List<Route> getRouteByCid(Integer cid, Integer page ,String search) {
        return rd.getRouteByCid(cid,page,search);
    }

    @Override
    public int getPageCountByCid(int i ,String search) {
        return rd.getPageCountByCid(i,search);
    }

    @Override
    public Route getRouteById(Integer rid) {
        Route route=rd.getRouteById(rid);
        Seller seller = sd.getSellerById(route.getSid());
        route.setSeller(seller);
        List<RouteImg> routeImgs = rd.routeImgList(rid);
        route.setRouteImgList(routeImgs);

        return route;
    }

    @Override
    public List<RouteImg> routeImgList(Integer rid) {
        return rd.routeImgList(rid);
    }

    @Override
    public RouteGetByIdPage getRouteGetByIdPage(int cid, int page, String search) {
        List<Route> routeByCid = rd.getRouteByCid(cid,page,search);
        int pagecount=rd.getPageCountByCid(cid,search);
        RouteGetByIdPage routeGetByIdPage = new RouteGetByIdPage(routeByCid,page,pagecount,cid,search);
        return routeGetByIdPage;
    }



    @Override
    public indexRoute getIndexRoute() {
        return new indexRoute(rd.getRoute(), rd.getNewRoute(), rd.getThemeRoute(), rd.Routeindex1(), rd.Routeindex2());
    }

    @Override
    public List<Route> getHotRoute() {
        return rd.getHotRoute();
    }


}
