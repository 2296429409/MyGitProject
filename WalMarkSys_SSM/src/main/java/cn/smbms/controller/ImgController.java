package cn.smbms.controller;

import cn.smbms.pojo.Img;
import cn.smbms.pojo.User;
import cn.smbms.service.ImgService;
import cn.smbms.tools.Base64Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.util.*;

@Controller
@RequestMapping("/img")
public class ImgController {

    @Autowired
    private ImgService imgService;

    @RequestMapping("/addImg")
    @ResponseBody
    public Map addImg(String imageFile, String finleName, String uid,HttpServletRequest request) {
        Map<String,String> resultMap = new HashMap<>();
        finleName = "."+finleName.trim();
        String newFileName = UUID.randomUUID().toString().replace("-","")+finleName;
        File file = Base64Utils.convertBase64ToFile(imageFile, "D://img", newFileName);
        if (file!=null) {
            Img img = new Img();
            String url = "http://192.168.4.27:8087/"+newFileName;
            img.setUrl(url);
            User loginUser = (User) request.getSession().getAttribute("userSession");
            if (uid!=null && !uid.equals("")) {
                img.setUserId(Integer.parseInt(uid));
            }else {
                img.setUserId(loginUser.getId());
            }
            //判断用户是否存在了
            Img imgByUserId = imgService.getImgByUserId(img.getUserId());
            boolean falg = false;
            if (imgByUserId!=null) {
                falg = imgService.updateImgByUserId(img);
            }else {
                falg = imgService.addImg(img);
            }
            if (falg) {
                resultMap.put("success","true");
                System.out.println("url:"+url);
                request.getSession().setAttribute("loginerImg",url);
            }else {
                resultMap.put("err","false");
            }
        }else {
            resultMap.put("err","false");
        }
        return resultMap;
    }

    @RequestMapping("/getImgList")
    @ResponseBody
    public List<Img> getImgList() {
        List<Img> imgList = new ArrayList<>();
        imgList = imgService.getImgList();
        System.out.println(imgList);
        return imgList;
    }

    @RequestMapping("/getImgByUserId")
    @ResponseBody
    public Img getImgByUserId(int userId) {
        Img img = new Img();
        img = imgService.getImgByUserId(userId);
        System.out.println(img);
        return img;
    }
}
