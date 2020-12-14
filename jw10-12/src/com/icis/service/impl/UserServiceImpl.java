package com.icis.service.impl;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.icis.dao.UserDao;
import com.icis.dao.impl.UserDaoImpl;
import com.icis.pojo.Login;
import com.icis.pojo.TbAddress;
import com.icis.pojo.User;
import com.icis.service.UserService;
import com.icis.utils.JedisUtils;
import redis.clients.jedis.Jedis;

import java.util.List;

public class UserServiceImpl implements UserService {
    private UserDao userDao;

    public void setUserDao(UserDao userDao) {
        this.userDao = userDao;
    }

//    private final static Jedis JEDIS= JedisUtils.getJedis();
    private static final ObjectMapper om =new ObjectMapper();
    @Override
    public Integer UserLogin(String user,String password) {
        return userDao.UserLogin(user,password);
    }

    @Override
    public int update(User user) {
        return userDao.update(user);
    }

    @Override
    public int delete(String id) {
        return userDao.delete(id);
    }

    @Override
    public int addUser(User user) {
        return userDao.addUser(user);
    }

    @Override
    public List<User> search(String name, String address, String email, Integer page) {
        return userDao.search(name,address,email,page);
    }

    @Override
    public Integer getI(String name, String address, String email) {
        return userDao.getI(name,address,email);
    }

    @Override
    public List<TbAddress> getAddresses() {
//        return userDao.getAddresses();
//        try{
//            String addresses = JEDIS.get("Addresses");
//            List<TbAddress> addressList;
//            if (addresses==null) {
//                addressList = userDao.getAddresses();
//                String strAddress = om.writeValueAsString(addressList);
//                JEDIS.set("Addresses",strAddress);
//            }else {
//                addressList = om.readValue(addresses, new TypeReference<List<TbAddress>>() {
//                });
//            }
//            return addressList;
//        }catch (Exception e){
            return null;
//        }
    }

    @Override
    public void AddLogin(Login login) {
        userDao.AddLogin(login);
    }

    @Override
    public void setDeletes() {
        userDao.setDeletes();
    }
}
