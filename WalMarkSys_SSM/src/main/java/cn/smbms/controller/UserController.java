package cn.smbms.controller;

import cn.smbms.pojo.Img;
import cn.smbms.pojo.MessageInfo;
import cn.smbms.pojo.User;
import cn.smbms.service.UserService;
import cn.smbms.service.impl.ImgServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@Controller
@SessionAttributes({"userSession","loginerImg"})
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;
    @Autowired
    private ImgServiceImpl imgService;
    @Autowired
    private HttpServletRequest request;


    @RequestMapping("/login.do")
    public String login(User user,Model model){
        System.out.println("login--->"+user);
        User loginer =  userService.login(user);
        if (loginer == null){
            model.addAttribute("error","账号或密码错误！！！");
            System.out.println("loginer:-->"+loginer);
            return "../login";
        }else {
            model.addAttribute("userSession",loginer);
            Img loginerImg = imgService.getImgByUserId(loginer.getId());
            model.addAttribute("loginerImg",loginerImg.getUrl());
            System.out.println("loginerImg:"+loginerImg.getUrl());
            System.out.println("loginer:-->"+loginer);
            return "frame";
        }
    }



    // 检查密码
    @RequestMapping("/pwdModify.do")
    @ResponseBody
    public MessageInfo pwdModify(String oldpassword){
        System.out.println("pwdModify.do-------"+oldpassword);
        User loginer = (User) request.getSession().getAttribute("userSession");
        MessageInfo messageInfo = new MessageInfo();
        if (loginer==null||loginer.getId() == null){
            messageInfo.setResult("sessionerror");
        }else if(oldpassword.equals("")){
            messageInfo.setResult("error");
        }else {
            // 密码加密
            oldpassword= DigestUtils.md5DigestAsHex(oldpassword.getBytes());
            boolean flag = loginer.getUserPassword().equals(oldpassword);
            if (flag){
                messageInfo.setResult("true");
            }else {
                messageInfo.setResult("false");
            }
        }
        return messageInfo;
    }



    // 执行密码修改 savepwd
    @RequestMapping("/savepwd.do")
    public String savepwd(String oldpassword, String newpassword ,String rnewpassword,Model model){
        System.out.println(" savepwd.do---------------------> "+newpassword);
        User loginer = (User) request.getSession().getAttribute("userSession");
        if (oldpassword!=null && newpassword!=null && rnewpassword!=null &&
                loginer.getUserPassword().equals(oldpassword)&&
                (!newpassword.equals(""))&& newpassword.equals(rnewpassword)
                ){
            loginer.setUserPassword(newpassword);
            Integer update = userService.updatePwd(loginer);
            if (update>0){
                model.addAttribute("message","修改成功！");
            }else {
                model.addAttribute("message","修改失败！");
            }
        }else {
            model.addAttribute("message","页面信息输入错误！");
        };
        return "pwdmodify";
    }

    // 登出 logout
    @RequestMapping("/logout.do")
    public String logout(Model model){
        // 可以写在拦截器  还有bug
        User user = new User();
        model.addAttribute("userSession",user);
        model.addAttribute("loginerImg","");
        return "../login";
    }



}
