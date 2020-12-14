package cn.smbms.service.impl;

import cn.smbms.mapper.ProviderMapper;
import cn.smbms.pojo.Provider;
import cn.smbms.pojo.Province;
import cn.smbms.service.JedisService;
import cn.smbms.service.ProviderService;
import cn.smbms.tools.PageSupport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProviderServiceImpl implements ProviderService {
    @Autowired
    private ProviderMapper providerMapper;
    @Autowired
    private JedisService js;
    @Override
    public List<Provider> queryAll() {
        return providerMapper.queryAll();
    }

    @Override
    public PageSupport likeQuery(String queryProCode, String queryProName, String currentPageNo, Integer pageSize, String isdelete) {
        return null;
    }

    @Override
    public PageSupport likeQuery(String queryProCode, String queryProName, String _currentPageNo, Integer pageSize, String _isdelete,String id) {
        if (_currentPageNo==null || "".equals(_currentPageNo)){
            _currentPageNo="1";
        }
        if (_isdelete==null || "".equals(_isdelete)){
            _isdelete="1";
        }
        //添加输入框搜索记录
        if (!(queryProName.equals("")||id.equals(""))){
            js.addsearchOne("providername"+id,queryProName);
        }
        if (!(queryProCode.equals("")||id.equals(""))){
            js.addsearchOne("providercode"+id,queryProCode);
        }
        Integer isdelete=Integer.parseInt(_isdelete);
        Integer currentPageNo=Integer.parseInt(_currentPageNo);
        PageSupport pageSupport=new PageSupport();
        pageSupport.setPageSize(pageSize);
        pageSupport.setCurrentPageNo(currentPageNo);
        //根据条件查询记录数
        Integer totalCount=providerMapper.getProviderCount(queryProCode,queryProName,isdelete);
        pageSupport.setTotalCount(totalCount);
        //每一页
        currentPageNo=(currentPageNo-1)*pageSize;
        List<Provider> providerList=providerMapper.likeQuery(queryProCode,queryProName,currentPageNo,pageSize,isdelete);
        pageSupport.setObjects(providerList);
/*        Integer isdelete1=1;
        if (!providerList.isEmpty()){
             isdelete1 = providerList.get(0).getIsdelete();
        }
        pageSupport.setIsdelete(isdelete1);*/
        return pageSupport;
    }
    /**
     * 业务：根据ID删除供应商表的数据之前，需要先去订单表里进行查询操作
     * 若订单表中无该供应商的订单数据，则可以删除
     * 若有该供应商的订单数据，则不可以删除
     * 返回值billCount
     * 1> billCount == 0  删除---1 成功 （0） 2 不成功 （-1）
     * 2> billCount > 0    不能删除 查询成功（0）查询不成功（-1）
     *
     * ---判断
     * 如果billCount = -1 失败
     * 若billCount >= 0 成功
     */
    @Override
    public int delProviderById(Integer id) {
        int billCount = -1;
        billCount=providerMapper.getbillCount(id);
        System.out.println("查到的数量:"+billCount);
        if (billCount==0){
            providerMapper.delProviderById(id);
        }
        return  billCount;
    }

    @Override
    public int addProvider(Provider provider) {
        String proCode=null;
        String second="_";
        String third="GY";
        int pid=provider.getPid();
        String pcode1=providerMapper.getPcodeById(pid);
        String pcode = pcode1.toUpperCase();
        int id1=providerMapper.addProvider(provider);
        Integer id = provider.getId();
        if (id/10==0){
            proCode=pcode+second+third+"00"+id;
            System.out.println(proCode);
        }else if (9<id&&id/10<10){
            proCode=pcode+second+third+"0"+id;
            System.out.println(proCode);
        }else if(id/100>=0){
            proCode=pcode+second+third+id;
            System.out.println(proCode);
        }
        provider.setProCode(proCode);
        return  providerMapper.modifyProvider(provider);
    }

    @Override
    public Provider getProviderById(int id) {
        return providerMapper.getProviderById(id);
    }

    @Override
    public boolean modifyProvider(Provider provider) {
       int flag=providerMapper.modifyProvider(provider);
       if (flag>0){
           return true;
       }else {
           return false;
       }
    }

    @Override
    public List<Province> getProvince() {
        return providerMapper.getProvince();
    }

    @Override
    public void recoveryProviderById(Integer id) {
        providerMapper.recoveryProviderById(id);
    }

    @Override
    public List<String> getProCodeInput(String proCode) {
        return providerMapper.getProCodeInput("%"+proCode+"%");
    }

    @Override
    public List<String> getProductNameInput(String proName) {
        return providerMapper.getProductNameInput("%"+proName+"%");
    }

    @Override
    public Object getdata1(String queryProCode) {
        Object getsearch;
        String jname="inputpro1"+queryProCode;
        if (js.IsNullData(jname)){
            System.err.println(jname+"notdata.....getdata..........");
            getsearch = this.getProCodeInput(queryProCode);
            js.setsearch(jname,getsearch);
        }else {
            System.err.println(jname+"isdata.....jedisget..........");
            getsearch = js.getdata(jname);
            System.out.println(getsearch);
        }
        return getsearch;
    }

    @Override
    public Object getdata2(String queryProductName) {
        System.out.println(queryProductName);
        Object getsearch;
        String jname="inputpro2"+queryProductName;
        if (js.IsNullData(jname)){
            System.err.println(jname+"notdata.....getdata..........");
            getsearch = this.getProductNameInput(queryProductName);
            js.setsearch(jname,getsearch);
        }else {
            System.err.println(jname+"isdata.....jedisget..........");
            getsearch = js.getdata(jname);
            System.out.println(getsearch);
        }
        return getsearch;
    }
}
