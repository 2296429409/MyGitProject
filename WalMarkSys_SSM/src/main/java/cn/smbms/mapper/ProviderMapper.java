package cn.smbms.mapper;

import cn.smbms.pojo.Provider;
import cn.smbms.pojo.Province;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ProviderMapper {

    List<Provider> queryAll();

    List<Provider> likeQuery(@Param("proCode") String queryProCode, @Param("proName") String queryProName, @Param("currentPageNo") Integer currentPageNo, @Param("pageSize") Integer pageSize, @Param("isdelete") Integer isdelete);

    int delProviderById(Integer id);

    int addProvider(Provider provider);

    Provider getProviderById(int id);

    int modifyProvider(Provider provider);

    int getbillCount(Integer id);

    Integer getProviderCount(@Param("proCode") String queryProCode, @Param("proName") String queryProName, @Param("isdelete") Integer isdelete);

    List<Province> getProvince();

    String getPcodeById(int pid);

    void recoveryProviderById(Integer id);

    List<String> getProCodeInput(String proCode);
    List<String> getProductNameInput(String proName);
}
