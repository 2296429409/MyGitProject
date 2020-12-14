package cn.smbms.controller;

import cn.smbms.pojo.Provider;
import cn.smbms.pojo.Province;
import cn.smbms.pojo.User;
import cn.smbms.service.ProviderService;
import cn.smbms.tools.PageSupport;
import com.mysql.jdbc.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

@Controller
@RequestMapping("/provider")
public class ProviderController {
    @Autowired
    HttpServletRequest request;
    @Autowired
    private ProviderService providerService;
    //查询所有记录
    //模糊查询
    @RequestMapping("/likeQuery.do")
    public ModelAndView likeQuery(String queryProCode,String queryProName,String pageIndex,String isdelete,HttpServletRequest request,String id){
        ModelAndView modelAndView=new ModelAndView();
        Integer pageSize=5;
        String currentPageNo=pageIndex;
        System.out.println(queryProCode+"-就是这里-"+queryProName+"---"+pageIndex+"----"+isdelete);
        if (StringUtils.isNullOrEmpty(queryProCode)){
            queryProCode = "";
        }else {
            modelAndView.addObject("queryProCode",queryProCode);
        }
        if (StringUtils.isNullOrEmpty(queryProName)){
            queryProName = "";
        }else {
            modelAndView.addObject("queryProName",queryProName);
        }
        if (StringUtils.isNullOrEmpty(id)){
            id = "";
        }
         Integer isdelete1;
        try{
            if (!"".equals(isdelete)&& isdelete!=null&&! isdelete.equals("null")){
                isdelete1 = Integer.parseInt(isdelete);
            }else {
                isdelete1=1;
            }
        }catch (Exception e){
            isdelete1=1;
            e.printStackTrace();
        }
        HttpSession session = request.getSession();
        session.setAttribute("isdelete",isdelete1);
        PageSupport providerList=providerService.likeQuery(queryProCode,queryProName,currentPageNo,pageSize,isdelete,id);
//        Integer isdelete1 = providerList.getIsdelete();
        Integer isdelete2 =(Integer) session.getAttribute("isdelete");
        System.out.println("-----------------"+isdelete1);
        modelAndView.addObject("paramProvider",providerList);
        modelAndView.addObject("isdelete1",isdelete2);
        modelAndView.setViewName("providerlist");
        List<String> stringList = new ArrayList<>();
        stringList.add(queryProCode);
        stringList.add(queryProName);
        modelAndView.addObject("stringList",stringList);
        return modelAndView;
    }
    //根据id删除
    @RequestMapping("/delProviderById.do")
    @ResponseBody
    public HashMap<String, String> delProviderById(Integer proid){
//        System.out.println("获得的Id"+proid);
        int id=proid;
        int flag= providerService.delProviderById(id);
//        System.out.println("返回数据"+flag);
        HashMap<String, String> resultMap = new HashMap<>();
        if (flag==0){
            resultMap.put("delResult", "true");
        }else if(flag == -1){//删除失败
            resultMap.put("delResult", "false");
        }else if(flag > 0){//该供应商下有订单，不能删除，返回订单数
            resultMap.put("delResult", String.valueOf(flag));
        }else {
            resultMap.put("delResult", "notexit");
        }
//        返回数据
        return resultMap;
    }
    //添加供应商
    @RequestMapping("/addProvider.do")
    public String addProvider(Provider provider,Model model,HttpServletRequest request){
        try{
            User loginer = (User) request.getSession().getAttribute("userSession");
            Integer id = loginer.getId();
            provider.setCreatedBy(id);
        }catch (Exception e){
            System.out.println("出错了!!!!!!");
            provider.setCreatedBy(1);
            e.printStackTrace();
        }
        provider.setCreationDate(new Date());
        System.out.println("添加地址后的供应商:"+provider);
       int flag=providerService.addProvider(provider);
        return "redirect:likeQuery.do";
    }
    //显示详细信息
    @RequestMapping("getProviderById.do")
    public String getProviderById(Integer proid,Model model){
        int id=proid;
        Provider provider=providerService.getProviderById(id);
        System.out.println(provider);
        model.addAttribute("provider",provider);
        return "providerview";
    }
    //修改数据回显
    @RequestMapping("/modifyBefore.do")
    public String modifyBefore(Integer proid,Model model){
//        System.out.println("获得的proid:"+proid);
        int id=proid;
        Provider provider=providerService.getProviderById(id);
        model.addAttribute("provider",provider);
        return "providermodify";
    }
    //修改数据
    @RequestMapping("/modifyProvider.do")
    public String modifyProvider(Provider provider,Model model,HttpServletRequest request){
        try {
            User loginer = (User) request.getSession().getAttribute("userSession");
            Integer uid = loginer.getId();
            provider.setModifyBy(uid);
        }catch (Exception e){
            System.out.println("出错了!!!!!!!");
            provider.setModifyBy(1);
            e.printStackTrace();
        }
        provider.setModifyDate(new Date());
        boolean flag=false;
//        System.out.println("获取的修改数据:"+provider);
        flag=providerService.modifyProvider(provider);
        if (flag){
            return "redirect:likeQuery.do";
        }else {
            System.out.println("修改失败!");
            return "providermodify";
        }
    }



    //联想查询1
    @RequestMapping("getdata1")
    @ResponseBody
    public Object getdata1(String queryProCode) {
        return providerService.getdata1(queryProCode);
    }

    //获取省份列表
    @RequestMapping("/getProvince.do")
    public String getProvince(Model model){
        List<Province>provinceList=providerService.getProvince();
//        System.out.println("省份输出:"+provinceList);
        model.addAttribute("provinceList",provinceList);
        return "provideradd";
    }

    //恢复供应商
    @RequestMapping("recoveryProviderById.do")
    public String recoveryProviderById(Model model,Integer proid,HttpServletRequest request){
            Integer id = proid;
            providerService.recoveryProviderById(id);
            return "redirect:likeQuery.do";

        }
    //联想查询2
    @RequestMapping("getdata2")
    @ResponseBody
    public Object getdata2(String queryProductName) {
        return providerService.getdata2(queryProductName);
    }


}
