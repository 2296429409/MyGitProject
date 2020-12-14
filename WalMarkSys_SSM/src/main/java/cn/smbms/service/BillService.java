package cn.smbms.service;

import cn.smbms.pojo.Bill;
import cn.smbms.pojo.Provider;

import java.util.List;

public interface BillService {
    List<Bill> getBillList(String queryProductName, String queryProviderId, String queryIsPayment,String id);

    List<Provider> getProviderList();

    Integer addBill(Bill bill);


    Bill getBillById(String billid);

    Integer updateBill(Bill bill);

    Integer delBill(String billid);

    //输入框搜索
    List<String> getBillInput(String productName);
    //jedis处理输入框搜索
    Object getdata(String queryProductName);
}
