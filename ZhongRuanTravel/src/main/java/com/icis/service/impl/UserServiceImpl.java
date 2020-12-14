package com.icis.service.impl;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.icis.dao.UserDao;
import com.icis.dao.impl.UserDaoImpl;
import com.icis.pojo.Category;
import com.icis.pojo.User;
import com.icis.service.UserService;
import com.icis.utils.MailUtils;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import java.io.IOException;
import java.util.List;

public class UserServiceImpl implements UserService {
    private UserDao userDao = new UserDaoImpl();
    private ObjectMapper om = new ObjectMapper();
    private JedisPool jedisPool = new JedisPool();
    private Jedis resource = jedisPool.getResource();

    @Override
    public void addUser(User user) {
        userDao.addUser(user);
        MailUtils.sendMail(user.getEmail(), "点击下面链接激活你的账号: http://192.168.4.31:8085/travel/activeUserServlet?code=" + user.getCode(), "激活你的账号");
    }

    @Override
    public int setStatus(String code) {
        return userDao.setStatus(code);
    }

    @Override
    public int loginUser(User user) {
        return userDao.loginUser(user);
    }

    @Override
    public int checkUserName(User user) {
        return userDao.checkUserName(user);
    }

    @Override
    public List<Category> getCategory() {
        try {
            String category = resource.get("category");
            if (category == "" || category == null) {
                List<Category> categorylist = userDao.getCategory();
                resource.set("category", om.writeValueAsString(categorylist));
                return categorylist;
            } else {
                return om.readValue(category, new TypeReference<List<Category>>() {
                });
            }
        } catch (Exception e) {
            return userDao.getCategory();
        }
    }
}
