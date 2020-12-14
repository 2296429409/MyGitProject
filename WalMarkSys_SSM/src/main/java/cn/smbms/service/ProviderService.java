package cn.smbms.service;

import cn.smbms.pojo.Provider;
import cn.smbms.pojo.Province;
import cn.smbms.tools.PageSupport;

import java.util.List;

public interface ProviderService {

    PageSupport likeQuery(String queryProCode, String queryProName, String currentPageNo, Integer pageSize, String isdelete,String id);
    List<Provider> queryAll();
    PageSupport likeQuery(String queryProCode, String queryProName, String currentPageNo, Integer pageSize, String isdelete);
//    List<Provider> queryAll();

    int delProviderById(Integer id);

    int addProvider(Provider provider);

    Provider getProviderById(int id);

    boolean modifyProvider(Provider provider);

    List<Province> getProvince();

    void recoveryProviderById(Integer id);

    //输入框搜索
    List<String> getProCodeInput(String proCode);
    List<String> getProductNameInput(String proName);
    //jedis处理输入框搜索
    Object getdata1(String queryProCode);
    Object getdata2(String queryProductName);
}
