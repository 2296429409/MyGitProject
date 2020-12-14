package cn.smbms.service;

import cn.smbms.pojo.Role;
import cn.smbms.pojo.User;

import java.util.List;

public interface UserService {

    List<User> getAllUser();//谢毓武

    User login(User user);//伍再园


    /**
     * 增加用户信息
     * @param user
     * @return
     */
    public void adduser(User user);

/*    *//**
     * 用户登录
     * @param userCode
     * @param userPassword
     * @return
     *//*
    public User login(String userCode,String userPassword);*/

    /**
     * 根据条件查询用户列表
     * @param queryUserName
     * @param queryUserRole
     * @return
     */
    public List<User> getUserList(String queryUserName, int queryUserRole, int currentPageNo, int pageSize,String id);
    /**
     * 根据条件查询用户表记录数
     * @param queryUserName
     * @param queryUserRole
     * @return
     */
    public int getUserCount(String queryUserName,int queryUserRole);

    /**
     * 根据userCode查询出User
     * @param userCode
     * @return
     */
    public User selectUserCodeExist(String userCode);

    /**
     * 根据ID删除user
     * @param delId
     * @return
     */
    public boolean deleteUserById(Integer delId);

    /**
     * 根据ID查找user
     * @param id
     * @return
     */
    public User getUserById(String id);

    /**
     * 修改用户信息
     * @param user
     * @return
     */
    public boolean modify(User user);



    Integer updatePwd(User loginer);

    List<Role> getrolelist();

    int getUserByCode(String userCode);

    void userupdate(User user);

    //输入框搜索
    List<String> getUserInput(String userName);
    //jedis处理输入框搜索
    Object getdata(String queryUserName);
}
