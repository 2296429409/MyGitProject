package test;

import cn.smbms.tools.JedisPoolUtils;
import redis.clients.jedis.Jedis;

import java.util.Set;

public class T1 {
    public static void main(String[] args) {
        Jedis jedis = JedisPoolUtils.getJedis();
//        jedis.set("123", "234");
//        jedis.set("1", "253");
//        String s = jedis.get("123");
//        System.out.println(s);
        Set<String> keys = jedis.keys("inputuser*");
        System.out.println(keys);



        jedis.del(jedis.keys("inputuser*").toArray(new String[0]));

        Set<String> keys1 = jedis.keys("inputuser*");
        System.out.println(keys1);


    }
}
