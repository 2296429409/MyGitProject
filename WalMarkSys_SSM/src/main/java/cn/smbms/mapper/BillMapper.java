package cn.smbms.mapper;

import cn.smbms.pojo.Bill;
import cn.smbms.pojo.Provider;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface BillMapper {
    List<Bill> getBillList(@Param("queryProductName")String queryProductName,
                           @Param("queryProviderId")String queryProviderId,
                           @Param("queryIsPayment") String queryIsPayment);

    List<Provider> getProviderList();

    Integer addBill(Bill bill);


    Bill getBillById(@Param("id")Integer id);

    Integer updateBill(Bill bill);

    Integer delBill(@Param("id")Integer id);

    List<String> getBillInput(String productName);
}
