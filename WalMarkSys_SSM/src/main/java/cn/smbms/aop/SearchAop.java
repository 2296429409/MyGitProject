package cn.smbms.aop;

import cn.smbms.service.JedisService;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


//放入spring容器
@Component
//声明为切面类
@Aspect
public class SearchAop {
    //更新信息时清除联想查询

    @Autowired
    private JedisService jedisService;


    //UserServiceImpl
    @Before("execution(* cn.smbms.service.impl.UserServiceImpl.adduser(*))")
    public void adduser(){
        jedisService.delinputuser();
    }

    @Before("execution(* cn.smbms.service.impl.UserServiceImpl.deleteUserById(*))")
    public void deleteUserById(){
        jedisService.delinputuser();
    }

    @Before("execution(* cn.smbms.service.impl.UserServiceImpl.userupdate(*))")
    public void userupdate(){
        jedisService.delinputuser();
    }

    //BillServiceImpl
    @Before("execution(* cn.smbms.service.impl.BillServiceImpl.addBill(*))")
    public void addBill(){
        jedisService.delinputbill();
    }

    @Before("execution(* cn.smbms.service.impl.BillServiceImpl.updateBill(*))")
    public void updateBill(){
        jedisService.delinputbill();
    }

    @Before("execution(* cn.smbms.service.impl.BillServiceImpl.delBill(*))")
    public void delBill(){
        jedisService.delinputbill();
    }

    //ProviderServiceImpl
    @Before("execution(* cn.smbms.service.impl.ProviderServiceImpl.delProviderById(*))")
    public void delProviderById(){
        jedisService.delinputpro();
    }

    @Before("execution(* cn.smbms.service.impl.ProviderServiceImpl.addProvider(*))")
    public void addProvider(){
        jedisService.delinputpro();
    }

    @Before("execution(* cn.smbms.service.impl.ProviderServiceImpl.modifyProvider(*))")
    public void modifyProvider(){
        jedisService.delinputpro();
    }

    @Before("execution(* cn.smbms.service.impl.ProviderServiceImpl.recoveryProviderById(*))")
    public void recoveryProviderById(){
        jedisService.delinputpro();
    }
}

