package cn.saladday.recruit_321cqu.service.impl;

import cn.saladday.recruit_321cqu.dao.UserMapper;
import cn.saladday.recruit_321cqu.entity.User;
import cn.saladday.recruit_321cqu.service.UserService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    @Override
    public Boolean checkUsernameAndPasswordFromDB(User user) {

        MessageDigest md = null;
        try {
            md = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException ignore) {
        }
        byte[] digest = md.digest(user.getPassword().getBytes());
        BigInteger number = new BigInteger(1,digest);
        user.setPassword(number.toString(16));

        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.eq("user_id",user.getUserId());
        User one = this.getOne(wrapper);
        if(!one.getPassword().equals(user.getPassword())){
            return false;
        }
        return true;

    }
}
