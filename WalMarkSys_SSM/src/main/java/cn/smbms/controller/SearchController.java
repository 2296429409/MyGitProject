package cn.smbms.controller;

import cn.smbms.service.JedisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Set;

@Controller
@RequestMapping("search")
public class SearchController {
    @Autowired
    private JedisService jedisService;

    @RequestMapping("getsearch")
    @ResponseBody
    public Set<String> getsearch(String key){
        return jedisService.getsearch(key);
    }

    @RequestMapping("clearsearch")
    public void clearsearch(String key){
        jedisService.delAll(key);
    }

}
