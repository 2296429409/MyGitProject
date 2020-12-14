package cn.smbms.controller;


import cn.smbms.pojo.Img;
import cn.smbms.pojo.Role;
import cn.smbms.pojo.User;
import cn.smbms.service.ImgService;
import cn.smbms.service.JedisService;
import cn.smbms.service.UserService;

import cn.smbms.tools.Constants;

import cn.smbms.tools.PageSupport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;


@Controller
@RequestMapping("usermanage")
public class UserManageController {

    @Autowired
    private UserService us;
    @Autowired
    private ImgService imgService;
    @Autowired
    private JedisService jedisService;

    private ModelAndView mav;



    @RequestMapping("userList.do")
    public ModelAndView getuserlist(String queryUserName, Integer queryUserRole, Integer pageIndex,String id) {
        int pageSize = Constants.pageSize;
        mav = new ModelAndView();
        if (queryUserName == null) {
            queryUserName = "";
        } else {
            mav.addObject("queryUserName", queryUserName);
        }
        if (queryUserRole == null) {
            queryUserRole = 0;
        } else {
            mav.addObject("queryUserRole", queryUserRole);
        }
        if (pageIndex == null) {
            pageIndex = 1;
        }

        List<User> userList = us.getUserList(queryUserName, queryUserRole, pageIndex, pageSize,id);
        List<Role> roles = us.getrolelist();


        mav.addObject("userlist", userList);
        mav.addObject("roleList", roles);

        PageSupport param = new PageSupport();
        param.setPageSize(Constants.pageSize);
        param.setCurrentPageNo(pageIndex);
        param.setTotalCount(us.getUserCount(queryUserName, queryUserRole));
        param.setTotalPageCountByRs();
        mav.addObject("ptest", param);

        System.err.println(param);
        mav.setViewName("userlist");
        return mav;
    }


    @RequestMapping("deleteuser")
    @ResponseBody
    public int deleteuser(Integer uid) {
        System.out.println("uid = " + uid);
        int i = -1;
        mav = new ModelAndView();
        boolean b = us.deleteUserById(uid);
        if (b == false)
            i = 0;
        if (b == true)
            i = 1;
        return i;
    }


    @RequestMapping("clearsearch")
    @ResponseBody
    public void clearsearch(String key) {
        System.out.println("clearsearch............");
        System.out.println(key);

    }


    //    "usermanage/useradd"
    @RequestMapping("useradd")
    public String useradd(User user) {
        System.out.println("useradd................");
        mav = new ModelAndView();
        user.setCreationDate(new Date());
        us.adduser(user);
        System.out.println("添加成功:" + user.getUserName());
        return "redirect:userList.do";
    }


    @RequestMapping("getrolelist")
    @ResponseBody
    public List<Role> getrolelist() {
        System.out.println("getrolelist................");

        List<Role> roles = us.getrolelist();


        return roles;
    }

    @RequestMapping("ucexist")
    @ResponseBody
    public String ucexist(String userCode) {
        System.out.println("ucexist................");
        int i = us.getUserByCode(userCode);
        if (i == 0) {
            return "true";
        } else {
            return "exist";
        }
    }

    //    window.location.href=path+"/usermanage/userview&uid="+ obj.attr("userid");
    @RequestMapping("userview")
    @ResponseBody
    public ModelAndView userview(String uid) {
        Img img = imgService.getImgByUserId(Integer.parseInt(uid));
        mav = new ModelAndView();
        User userById = us.getUserById(uid);
        System.out.println(userById);
        mav.addObject("user",userById);
        System.out.println(img);
        if (img!=null){
            mav.addObject("imgurl",img.getUrl());
        }else
            mav.addObject("imgurl","");
        mav.setViewName("userview");
        return mav;
    }

    //    window.location.href=path+"/usermanage/usermodify&uid="+ obj.attr("userid");
    @RequestMapping("usermodify")
    @ResponseBody
    public ModelAndView usermodify(String uid) {
        mav = new ModelAndView();
        User userById = us.getUserById(uid);
        mav.addObject("user",userById);

        mav.setViewName("usermodify");
        return mav;
    }

    @RequestMapping("modifyexe")
    public String modifyexe(User user) {
        us.userupdate(user);
        return "redirect:userList.do";
    }


    @RequestMapping("getdata")
    @ResponseBody
    public Object getdata(String queryUserName) {
        return us.getdata(queryUserName);
    }


    @RequestMapping("test")
    public void test(String name) {
        List<String> userInput = us.getUserInput(name);
        System.out.println(userInput);
    }

}
