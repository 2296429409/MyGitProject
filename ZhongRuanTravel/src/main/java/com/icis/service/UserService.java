package com.icis.service;

import com.icis.pojo.Category;
import com.icis.pojo.User;

import java.util.List;

public interface UserService {
    public void addUser(User user);

    int setStatus(String code);

    int loginUser(User user);

    int checkUserName(User user);

    List<Category> getCategory();
}
