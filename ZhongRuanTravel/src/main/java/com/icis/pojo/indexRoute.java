package com.icis.pojo;

import java.util.List;

public class indexRoute {
    private List<Route> routes1;
    private List<Route> routes2;
    private List<Route> routes3;
    private List<Route> routes4;
    private List<Route> routes5;

    public indexRoute() {
    }

    public indexRoute(List<Route> routes1, List<Route> routes2, List<Route> routes3, List<Route> routes4, List<Route> routes5) {
        this.routes1 = routes1;
        this.routes2 = routes2;
        this.routes3 = routes3;
        this.routes4 = routes4;
        this.routes5 = routes5;
    }

    public List<Route> getRoutes1() {
        return routes1;
    }

    public void setRoutes1(List<Route> routes1) {
        this.routes1 = routes1;
    }

    public List<Route> getRoutes2() {
        return routes2;
    }

    public void setRoutes2(List<Route> routes2) {
        this.routes2 = routes2;
    }

    public List<Route> getRoutes3() {
        return routes3;
    }

    public void setRoutes3(List<Route> routes3) {
        this.routes3 = routes3;
    }

    public List<Route> getRoutes4() {
        return routes4;
    }

    public void setRoutes4(List<Route> routes4) {
        this.routes4 = routes4;
    }

    public List<Route> getRoutes5() {
        return routes5;
    }

    public void setRoutes5(List<Route> routes5) {
        this.routes5 = routes5;
    }
}
