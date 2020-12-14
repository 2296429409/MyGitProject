package com.icis.dao.impl;

import com.icis.dao.RouteDao;
import com.icis.pojo.Route;
import com.icis.pojo.RouteImg;
import com.icis.pojo.Seller;
import com.icis.pojo.indexRoute;
import com.icis.utils.JDBCUtils;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;


import java.util.List;

public class RouteDaoImpl implements RouteDao {
    private final JdbcTemplate template = new JdbcTemplate(JDBCUtils.getDataSource());
    @Override
    public List<Route> getRoute() {
        String sql="select * from tab_route order by price limit 0,4";
        return template.query(sql, new BeanPropertyRowMapper<>(Route.class));
    }

    @Override
    public List<Route> getThemeRoute() {
        String sql="select * from tab_route where isThemeTour=1 limit 0,4";
        return template.query(sql, new BeanPropertyRowMapper<>(Route.class));
    }

    @Override
    public List<Route> getNewRoute() {
        String sql="select * from tab_route order by rdate DESC limit 0,4";
        return template.query(sql, new BeanPropertyRowMapper<>(Route.class));
    }

    @Override
    public List<Route> getRouteByCid(Integer cid, Integer page,String search) {
        StringBuilder sql=new StringBuilder();
        sql.append("select * from tab_route where ");
        if (cid!=0){
            sql.append(" cid="+cid+" and ");
        }
        if (!(search==null||search.equals(""))){
            sql.append(" rname like '%"+search+"%' and ");
        }
        sql.append(" cid IS NOT NULL order by rid limit ?,10");
        return template.query(new String(sql), new BeanPropertyRowMapper<>(Route.class),(page-1)*10);
    }

    @Override
    public int getPageCountByCid(int cid,String search) {
        StringBuilder sql=new StringBuilder();
        sql.append("select count(*) from tab_route where ");
        if (cid!=0){
            sql.append(" cid="+cid+" and ");
        }
        if (!(search==null||search.equals(""))){
            sql.append(" rname like '%"+search+"%' and ");
        }
        sql.append(" cid IS NOT NULL ");
        return template.queryForObject(new String(sql), Integer.class);
    }

    @Override
    public Route getRouteById(Integer rid) {
        String sql="select * from tab_route where rid=?";
        return template.queryForObject(sql, new BeanPropertyRowMapper<>(Route.class),rid);
    }

    @Override
    public List<RouteImg> routeImgList(Integer rid) {
        String sql="select * from tab_route_img where rid=?";
        return template.query(sql,new BeanPropertyRowMapper<>(RouteImg.class),rid);
    }

    @Override
    public List<Route> Routeindex1() {
        String sql="select * from tab_route where cid=5 group by rid limit 0,6";
        return template.query(sql, new BeanPropertyRowMapper<>(Route.class));
    }

    @Override
    public List<Route> Routeindex2() {
        String sql="select * from tab_route where cid=4 group by rid limit 0,6";
        return template.query(sql, new BeanPropertyRowMapper<>(Route.class));
    }

    @Override
    public List<Route> getHotRoute() {
        String sql="select * from tab_route group by price desc limit 0,5";
        return template.query(sql, new BeanPropertyRowMapper<>(Route.class));
    }
}
