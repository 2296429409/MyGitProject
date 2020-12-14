package com.icis.dao.impl;

import com.icis.dao.UserDao;
import com.icis.pojo.Category;
import com.icis.pojo.User;
import com.icis.utils.JDBCUtils;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;

public class UserDaoImpl implements UserDao {
    private final JdbcTemplate jdbcTemplate = new JdbcTemplate(JDBCUtils.getDataSource());
    @Override
    public void addUser(User user) {
        String sql="insert into tab_user values (null,?,?,?,?,?,?,?,'N',?)";
        jdbcTemplate.update(sql,user.getUsername(),user.getPassword(),user.getName(),user.getBirthday(),user.getSex(),user.getTelephone(),user.getEmail(),user.getCode());
    }

    @Override
    public int setStatus(String code) {
        String sql="update tab_user set status='Y' where code=?";
        return jdbcTemplate.update(sql,code);
    }

    @Override
    public int loginUser(User user) {
        String sql="select count(*) from tab_user where username=? and password=? and status='Y'";
        return jdbcTemplate.queryForObject(sql, Integer.class,user.getUsername(),user.getPassword());
    }

    @Override
    public int checkUserName(User user) {
        String sql="select count(*) from tab_user where username=?";
        return jdbcTemplate.queryForObject(sql, Integer.class,user.getUsername());
    }

    @Override
    public List<Category> getCategory() {
        JdbcTemplate template = new JdbcTemplate(JDBCUtils.getDataSource());
        String sql="select * from tab_category";
        return template.query(sql, new BeanPropertyRowMapper<Category>(Category.class));
    }
}
