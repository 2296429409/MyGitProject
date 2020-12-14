package cn.smbms.service.impl;

import cn.smbms.mapper.UserMapper;
import cn.smbms.pojo.Img;
import cn.smbms.pojo.Role;
import cn.smbms.pojo.User;
import cn.smbms.service.JedisService;
import cn.smbms.service.ImgService;
import cn.smbms.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {


    @Autowired
    private UserMapper userMapper;
    @Autowired
    private ImgService imgService;

    @Autowired
    private JedisService js;

    /**
     * 谢毓武
     * @return
     */
    @Override
    public List<User> getAllUser() {
        return userMapper.getAllUser();
    }

    @Override
    public User login(User user) {
        // MD5加密
//        String md5psw = DigestUtils.md5DigestAsHex(user.getUserPassword().getBytes());
//        user.setUserPassword(md5psw);
        return userMapper.login(user);
    }

    @Override
    public void adduser(User user) {
        userMapper.adduser(user);
        Integer userId = user.getId();
        System.out.println("新添加用户是："+user);

        Img img = new Img();
        img.setUserId(userId);
        imgService.addImg(img);
    }

    @Override
    public List<User> getUserList(String queryUserName, int queryUserRole, int currentPageNo, int pageSize,String id) {
        if (!(queryUserName.equals("")||id.equals(""))){
            js.addsearchOne("user"+id,queryUserName);
        }
        int befornumber= (currentPageNo-1)*pageSize;
        return userMapper.getUserList( "%"+queryUserName+"%", queryUserRole, befornumber, pageSize);
    }

    @Override
    public int getUserCount(String queryUserName, int queryUserRole) {
        return userMapper.getUserCount("%"+queryUserName+"%",queryUserRole);
    }

    @Override
    public User selectUserCodeExist(String userCode) {
        return null;
    }

    @Override
    public boolean deleteUserById(Integer delId) {
        int i = userMapper.deleteUserById(delId);
        if (i==0)
            return false;
        else
            return true;
    }

    @Override
    public User getUserById(String id) {
        User userById = userMapper.getUserById(id);
        userById.setUserRoleName(userMapper.getUserRoleName(id));
        return userById;
    }

    @Override
    public boolean modify(User user) {
        return false;
    }

    @Override
    public Integer updatePwd(User loginer) {
        // MD5加密
        String md5psw = DigestUtils.md5DigestAsHex(loginer.getUserPassword().getBytes());
        loginer.setUserPassword(md5psw);
        return userMapper.updatePwd(loginer);
    }

    @Override
    public List<Role> getrolelist() {
        if (js.IsNullData("rolelist")){
            List<Role> roleList = userMapper.getrolelist();
            js.setsearch("rolelist",roleList);
            return roleList;
        }else {
            return js.getrolelist();
        }
    }

    @Override
    public int getUserByCode(String userCode) {
        return userMapper.getUserByCode(userCode);
    }

    @Override
    public void userupdate(User user) {
        user.setModifyDate(new Date());
        userMapper.userupdate(user);
    }

    @Override
    public List<String> getUserInput(String userName) {
        return userMapper.getUserInput("%"+userName+"%");
    }

    @Override
    public Object getdata(String queryUserName) {
        Object getsearch;
        String jname="inputuser"+queryUserName;
        if (js.IsNullData(jname)){
            System.err.println(jname+"notdata.....getdata..........");
            getsearch = this.getUserInput(queryUserName);
            js.setsearch(jname,getsearch);
        }else {
            System.err.println(jname+"isdata.....jedisget..........");
            getsearch = js.getdata(jname);
            System.out.println(getsearch);
        }
        return getsearch;
    }


}
