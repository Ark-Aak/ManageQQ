package plugin.manageqq.database;

import plugin.manageqq.Config;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

import java.util.Objects;

public class RedisUtil {

    private static JedisPool pool;
    private static boolean isInit;

    public static void init(){
        isInit=true;
        JedisPoolConfig config = new JedisPoolConfig();
        int ConnectionCount = Integer.parseInt(Config.getDatabaseInfoRedis("ConnectionCount"));
        config.setMaxIdle(ConnectionCount);
        config.setMaxTotal(ConnectionCount);
        if(Config.getDatabaseInfoRedis("Password").equals("None")){
            JedisPool pool = new JedisPool(
                    config,
                    Config.getDatabaseInfoRedis("Url"),
                    Integer.parseInt(Config.getDatabaseInfoRedis("Port")),
                    2000
            );
        }
        else{
            JedisPool pool = new JedisPool(
                    config,
                    Config.getDatabaseInfoRedis("Url"),
                    Integer.parseInt(Config.getDatabaseInfoRedis("Port")),
                    2000,
                    Config.getDatabaseInfoRedis("Password")
            );
        }
    }

    public static Jedis getdb(){
        if(!isInit){
            init();
        }
        return pool.getResource();
    }

    public static void set(String key,String val){
        getdb().set(key, val);
    }

    public static void set(long key,long val){
        getdb().set(String.valueOf(key), String.valueOf(val));
    }

    public static void set(String key,long val){
        getdb().set(key, String.valueOf(val));
    }

    public static void set(long key,String val){
        getdb().set(String.valueOf(key), val);
    }

    public static void set(String key,String val,long second){
        getdb().setex(key, second, val);
    }

    public static void set(long key,long val,long second){
        getdb().setex(String.valueOf(key), second, String.valueOf(val));
    }

    public static void set(String key,long val,long second){
        getdb().setex(key, second, String.valueOf(val));
    }

    public static void set(long key,String val,long second){
        getdb().setex(String.valueOf(key), second, val);
    }

    public static String get(String key){
        return getdb().get(key);
    }

    public static String get(long key){
        return getdb().get(String.valueOf(key));
    }

    public static long getLong(String key){
        return Long.parseLong(getdb().get(key));
    }

    public static long getLong(long key){
        return Long.parseLong(getdb().get(String.valueOf(key)));
    }
}
