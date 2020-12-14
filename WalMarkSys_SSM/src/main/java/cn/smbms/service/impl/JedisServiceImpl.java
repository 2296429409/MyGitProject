package cn.smbms.service.impl;

import cn.smbms.pojo.Role;
import cn.smbms.service.JedisService;
import cn.smbms.tools.JedisPoolUtils;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;

import java.io.IOException;
import java.util.Date;

import java.util.List;
import java.util.Set;

@Service
public class JedisServiceImpl implements JedisService {

    private Jedis jedis = JedisPoolUtils.getJedis();
    private ObjectMapper om=new ObjectMapper();

    public void setsearch(String jname,Object o){
        String jvalue="";
        try {
            jvalue = om.writeValueAsString(o);
        } catch (JsonProcessingException e) {
            System.err.println("JSON转失败");
        }
        jedis.set(jname,jvalue);
    }

    @Override
    public boolean IsNullData(String jname) {
        if (jedis.get(jname)==null)
            return true;
        else
            return false;
    }

    @Override
    public List<String> getdata(String jname) {
        List<String> list=null;
        try {
            list=om.readValue(jedis.get(jname), new TypeReference<List< String >>() { });
        } catch (IOException e) {
            System.err.println("getdata方法转换失败!");
        }
        return list;
    }

    @Override
    public void delinputuser() {
        try {
            jedis.del(jedis.keys("inputuser*").toArray(new String[0]));
        } catch (Exception e) {
        }
    }

    @Override
    public void delinputpro() {
        try {
            jedis.del(jedis.keys("inputpro1*").toArray(new String[0]));
        } catch (Exception e) {
        }
        try {
            jedis.del(jedis.keys("inputpro2*").toArray(new String[0]));
        } catch (Exception e) {
        }
    }

    @Override
    public void delinputbill() {
        try {
            jedis.del(jedis.keys("inputbill*").toArray(new String[0]));
        } catch (Exception e) {
        }
    }

    @Override
    public void delAll(String jname) {
        try {
            jedis.del(jname);
        } catch (Exception e) {
        }
    }

    @Override
    public void addsearchOne(String jname, String jvalue) {
        Long len = jedis.zcard(jname);
        if (len>=10)
            jedis.zremrangeByRank(jname,9,jedis.zcard(jname)-1);
        jedis.zadd(jname,1-new Date().getTime(),jvalue);
    }

    @Override
    public Set<String> getsearch(String jname) {
        return jedis.zrange(jname, 0, 4);
    }

    @Override
    public List<Role> getrolelist() {
        List<Role> list=null;
        try {
            list=om.readValue(jedis.get("rolelist"), new TypeReference<List< Role >>() { });
        } catch (IOException e) {
            System.err.println("getrolelist方法转换失败!");
        }
        return list;
    }
}
