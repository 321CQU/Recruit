package cn.saladday.recruit_321cqu.common;

import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.SerializationException;

import java.nio.charset.Charset;

/*
自定义 redis Key 序列化器
 */
@Slf4j
public class RedisKeySerializer implements RedisSerializer<String> {
    private final String PREFIX_KEY = "recruit_321_cqu:";
    private final Charset charset;
    /*
    默认u8字符集
     */
    public RedisKeySerializer(){
        this(Charset.forName("UTF-8"));
    }

    public RedisKeySerializer(Charset charset) {
        this.charset = charset;
    }


    @Override
    public byte[] serialize(String string) throws SerializationException {
        if ("".equals(string)) {
            return null;
        }
        String key = PREFIX_KEY + string;
        return key.getBytes(charset);
    }

    @Override
    public String deserialize(byte[] bytes) throws SerializationException {

        String saveKey = new String(bytes, charset);
        if (saveKey==null || "".equals(saveKey)) {
            return null;
        }
        int indexOf = saveKey.indexOf(":");

        saveKey = saveKey.substring(indexOf+1);


        return saveKey;

    }
}
