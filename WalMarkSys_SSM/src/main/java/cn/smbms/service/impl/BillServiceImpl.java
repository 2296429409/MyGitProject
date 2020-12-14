package cn.smbms.service.impl;

import cn.smbms.mapper.BillMapper;
import cn.smbms.pojo.Bill;
import cn.smbms.pojo.Provider;
import cn.smbms.service.BillService;
import cn.smbms.service.JedisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Service
public class BillServiceImpl implements BillService {

    @Autowired
    private BillMapper billMapper;

    @Autowired
    private JedisService js;

    @Override
    public List<Bill> getBillList(String queryProductName, String queryProviderId, String queryIsPayment,String id) {
        if (queryProductName != null && !"".equals(queryProductName)){
            if (!(id.equals("")))
                js.addsearchOne("bill"+id,queryProductName);
            queryProductName=queryProductName.trim();
            queryProductName= queryProductName.replace(" ", "%");
            //添加搜索记录
        }
        return billMapper.getBillList(queryProductName,queryProviderId,queryIsPayment);
    }

    @Override
    public List<Provider> getProviderList() {
        return billMapper.getProviderList();
    }

    @Override
    public Integer addBill(Bill bill) {

        bill.setProductName( bill.getProductName().replaceAll(" ", ""));
        Integer integer = billMapper.addBill(bill);
        //日期类
        Calendar calendar=Calendar.getInstance();
        //设置增加日期进日期类
        calendar.setTime(bill.getCreationDate());
        //获得当前年份
        int i = calendar.get(Calendar.YEAR);
        //指定订单编号
        String billCode="BILL"+String.valueOf(i)+"_";
        if (bill.getId()>=0&&bill.getId()<10){
            billCode=billCode+"00"+bill.getId();
        }else if (bill.getId()>=10&&bill.getId()<100){
            billCode=billCode+"0"+bill.getId();
        }else if (bill.getId()>=100){
            billCode=billCode+bill.getId();
        }
        bill.setBillCode(billCode);
        billMapper.updateBill(bill);

        return integer ;
    }



    @Override
    public Bill getBillById(String id) {
        return billMapper.getBillById(Integer.parseInt(id));
    }

    @Override
    public Integer updateBill(Bill bill) {
        return billMapper.updateBill(bill);
    }

    @Override
    public Integer delBill(String id) {
        return billMapper.delBill(Integer.parseInt(id)) ;
    }

    @Override
    public List<String> getBillInput(String productName) {
        return billMapper.getBillInput("%"+productName+"%");
    }

    @Override
    public Object getdata(String queryProductName) {
        Object getsearch;
        String jname="inputbill"+queryProductName;
        if (js.IsNullData(jname)){
            System.err.println(jname+"notdata.....getdata..........");
            getsearch = this.getBillInput(queryProductName);
            js.setsearch(jname,getsearch);
        }else {
            System.err.println(jname+"isdata.....jedisget..........");
            getsearch = js.getdata(jname);
            System.out.println(getsearch);
        }
        return getsearch;
    }


}
