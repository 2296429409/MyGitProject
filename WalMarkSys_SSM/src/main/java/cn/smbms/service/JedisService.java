package cn.smbms.service;

import cn.smbms.pojo.Role;

import java.util.List;
import java.util.Set;

public interface JedisService {
    //有序集合类型 sortedset存储数据,最多10条

    //添加联想查询信息
    void setsearch(String jname,Object o);
    boolean IsNullData(String jname);
    List<String> getdata(String jname);
    //联想查询删除
    void delinputuser();
    void delinputpro();
    void delinputbill();

    //删除
    void delAll(String jname);
    //添加搜索记录
    void addsearchOne(String jname,String jvalue);
    //获得最新5条记录
    Set<String> getsearch(String jname);


    List<Role> getrolelist();
}
