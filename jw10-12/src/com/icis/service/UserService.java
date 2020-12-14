package com.icis.service;

import com.icis.pojo.Login;
import com.icis.pojo.TbAddress;
import com.icis.pojo.User;

import java.util.List;

public interface UserService {
    Integer UserLogin(String user,String password);

    int update(User user);

    int delete(String id);

    int addUser(User user);

    List<User> search(String name, String address, String email, Integer page);

    Integer getI(String name, String address, String email);

    List<TbAddress> getAddresses();

    void AddLogin(Login login);

    void setDeletes();
}
