package com.icis.dao.impl;

import com.icis.dao.UserDao;
import com.icis.pojo.Login;
import com.icis.pojo.TbAddress;
import com.icis.pojo.User;
import com.icis.utils.JDBCUtils;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;

public class UserDaoImpl implements UserDao {
    private JdbcTemplate template=new JdbcTemplate(JDBCUtils.getDataSource());


    @Override
    public Integer getI(String name, String address, String email) {
        String sql="SELECT count(*) FROM USER where showuser=1 ";
        if (!(name==null||"".equals(name)))
            sql=sql+" and name like '%"+name+"%'";
        if (!(address==null||"".equals(address)))
            sql=sql+" and address like '%"+address+"%'";
        if (!(email==null||"".equals(email)))
            sql=sql+" and email like '%"+email+"%'";
        Integer i = template.queryForObject(sql, Integer.class);
        return i;
    }

    @Override
    public Integer UserLogin(String user,String password) {
        String sql = "SELECT COUNT(*) FROM login WHERE USER=? AND PASSWORD=?";
        return template.queryForObject(sql, Integer.class, user,password);
    }

    @Override
    public int update(User user) {
        String sql="UPDATE USER SET NAME=?,gender=?,age=?,address=?,qq=?,email=? WHERE id=?";
        int i=template.update(sql,user.getName(),user.getGender(),user.getAge(),user.getAddress(),user.getQq(),user.getEmail(),user.getId());
        return i;
    }

    @Override
    public int delete(String id) {
        String sql="UPDATE USER SET showuser=0 WHERE id=?";
        int i=template.update(sql,id);
        return i;
    }

    @Override
    public int addUser(User user) {
        String sql="INSERT INTO USER VALUES (NULL,?,?,?,?,?,?,1)";
        int i=template.update(sql,user.getName(),user.getGender(),user.getAge(),user.getAddress(),user.getQq(),user.getEmail());
        return i;
    }

    @Override
    public List<User> search(String name, String address, String email,Integer page) {
        String sql="Select * from user where showuser=1 ";
        if (!(name==null||"".equals(name)))
            sql=sql+" and name like '%"+name+"%'";
        if (!(address==null||"".equals(address)))
            sql=sql+" and address like '%"+address+"%'";
        if (!(email==null||"".equals(email)))
            sql=sql+" and email like '%"+email+"%'";
        sql=sql+" LIMIT ? ,4";
        List<User> users = template.query(sql, new BeanPropertyRowMapper<User>(User.class),(page-1)*4);
        return users;
    }

    @Override
    public List<TbAddress> getAddresses() {
        String sql="select * from tb_address";
        List<TbAddress> addresses = template.query(sql, new BeanPropertyRowMapper<TbAddress>(TbAddress.class));
        return addresses;
    }

    @Override
    public void AddLogin(Login login) {
        String sql="INSERT INTO login VALUES(?,?,NULL)";
        template.update(sql,login.getUser(),login.getPassword());
    }

    @Override
    public void setDeletes() {
        String sql="UPDATE USER SET showuser=1";
        template.update(sql);
    }
}
