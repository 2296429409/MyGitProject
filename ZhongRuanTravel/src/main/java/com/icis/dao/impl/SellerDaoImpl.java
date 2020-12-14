package com.icis.dao.impl;

import com.icis.dao.SellerDao;
import com.icis.pojo.Route;
import com.icis.pojo.Seller;
import com.icis.utils.JDBCUtils;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

public class SellerDaoImpl implements SellerDao {
    private JdbcTemplate jdbcTemplate = JDBCUtils.getJdbcTemplate();
    @Override
    public Seller getSellerById(Integer sid) {
        String sql="select * from tab_seller where sid=?";
        return jdbcTemplate.queryForObject(sql, new BeanPropertyRowMapper<>(Seller.class),sid);
    }
}
