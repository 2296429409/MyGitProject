package cn.smbms.mapper;

import cn.smbms.pojo.Role;
import cn.smbms.pojo.User;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface UserMapper {


    List<User> getAllUser();

    public User login(User user);


    /**
     * 增加用户信息
     */
    public void adduser(User user);

    /**
     * 根据条件查询用户列表
     * @param queryUserName
     * @param queryUserRole
     * @return
     */
    public List<User> getUserList(@Param("queryUserName")String queryUserName, @Param("queryUserRole")int queryUserRole, @Param("befornumber")int befornumber, @Param("pageSize")int pageSize);
    /**
     * 根据条件查询用户表记录数
     * @param queryUserName
     * @param queryUserRole
     * @return
     */
    public int getUserCount(@Param("queryUserName")String queryUserName,@Param("queryUserRole")int queryUserRole);

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
    public int deleteUserById(Integer delId);

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

    String getUserRoleName(String id);

    void userupdate(User user);

    List<String> getUserInput(String userName);
}
