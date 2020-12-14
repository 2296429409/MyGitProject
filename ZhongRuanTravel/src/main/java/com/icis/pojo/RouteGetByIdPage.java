package com.icis.pojo;

import java.util.List;

public class RouteGetByIdPage {
    private List<Route> routes;
    private Integer thispage;
    private Integer pageSet;
    private Integer cid;
    private String search;

    public RouteGetByIdPage() {
    }

    public RouteGetByIdPage(List<Route> routes, Integer thispage, Integer pageSet, Integer cid, String search) {
        this.routes = routes;
        this.thispage = thispage;
        this.pageSet = pageSet;
        this.cid = cid;
        this.search = search;
    }

    public List<Route> getRoutes() {
        return routes;
    }

    public void setRoutes(List<Route> routes) {
        this.routes = routes;
    }

    public Integer getThispage() {
        return thispage;
    }

    public void setThispage(Integer thispage) {
        this.thispage = thispage;
    }

    public Integer getPageSet() {
        return pageSet;
    }

    public void setPageSet(Integer pageSet) {
        this.pageSet = pageSet;
    }

    public Integer getCid() {
        return cid;
    }

    public void setCid(Integer cid) {
        this.cid = cid;
    }

    public String getSearch() {
        return search;
    }

    public void setSearch(String search) {
        this.search = search;
    }
}
