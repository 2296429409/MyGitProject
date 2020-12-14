package com.icis.dao;

import com.icis.pojo.Category;
import com.icis.pojo.User;

import java.util.List;

public interface UserDao {
    void addUser(User user);

    int setStatus(String code);

    int loginUser(User user);

    int checkUserName(User user);

    List<Category> getCategory();
}
