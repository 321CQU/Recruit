package com.dxy.utils;

import com.dxy.pojo.Cache;

import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @Author: JasonD
 * @date: 2023/3/16 17:15
 * @Description:
 * 本地缓存工具类，基于定时器Timer
 */
public class CacheUtils {

    //默认大小
    private static final int DEFAULT_CAPACITY = 1024;

    //最大缓存大小
    private static final int MAX_CAPACITY = 10000;

    //默认缓存过期时间
    private static final long DEFAULT_TIMEOUT = 18000;

    //1000毫秒
    private static final long SECOND_TIME = 1000;

    //存储缓存Map
    private static final ConcurrentHashMap<String, Cache> map = new ConcurrentHashMap<>(DEFAULT_CAPACITY);

    //定时器配置
    private static final Timer timer = new Timer();

    //私有化构造器
    private CacheUtils() {
    }

    //缓存任务清除类
    static class ClearTask extends TimerTask {
        private String key;

        public ClearTask(String key) {
            this.key = key;
        }

        @Override
        public void run() {
            CacheUtils.remove(key);
        }
    }

    //CRUD
    //添加缓存
    public static boolean put(String key, Cache cache) {
        if (checkCapacity()) {
            map.put(key, cache);
            //用计时器来设置默认缓存时间，达到一定时间后自动清除缓存
            timer.schedule(new ClearTask(key), DEFAULT_TIMEOUT);
            return true;
        }
        return false;
    }

    public static boolean put(String key, Cache cache, int time_out) {
        if (checkCapacity()) {
            map.put(key, cache);
            timer.schedule(new ClearTask(key), time_out * SECOND_TIME);
            return true;
        }
        return false;
    }

    //容量判断
    public static boolean checkCapacity() {
        return map.size() < MAX_CAPACITY;
    }

    //批量增加
    public static boolean put(Map<String, Cache> m, int time_out) {
        if (map.size() + m.size() <= MAX_CAPACITY) {
            map.putAll(m);
            for (String key : m.keySet()) {
                timer.schedule(new ClearTask(key), time_out * SECOND_TIME);
            }
            return true;
        }
        return false;
    }

    //删除缓存
    public static void remove(String key) {
        map.remove(key);
    }

    //清除所有缓存
    public static void clearAll() {
        if (map.size() > 0) {
            map.clear();
        }
        timer.cancel();
    }

    //获取缓存
    public static Cache get(String key) {
        return map.get(key);
    }

    //判断是否存储缓存
    public static boolean isContain(String key) {
        return map.containsKey(key);
    }
}
