package cn.saladday.recruit_321cqu.utils;

import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

@Slf4j
@Component
public class RedisUtils {

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    /**
     * 根据key读取数据
     */
    public Object get(final String key) {
        if (StringUtils.isBlank(key)) {
            return null;
        }
        try {
            return redisTemplate.opsForValue().get(key);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 写入数据
     */
    public boolean set(final String key, Object value,Integer time) {
        if (StringUtils.isBlank(key)) {
            return false;
        }
        try {
            redisTemplate.opsForValue().set(key, value,time, TimeUnit.SECONDS);
            log.info("存入redis成功，key：{}，value：{}", key, value);
            return true;
        } catch (Exception e) {
            log.error("存入redis失败，key：{}，value：{}", key, value);
            e.printStackTrace();
        }
        return false;
    }

    /**
     * 默认一小时过期时间
     * @param key
     * @param value
     * @return
     */
    public boolean set(final String key,Object value){
        return this.set(key,value,60*60);
    }
}