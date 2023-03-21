package cn.saladday.recruit_321cqu.service;

import cn.saladday.recruit_321cqu.entity.User;
import com.baomidou.mybatisplus.extension.service.IService;

import java.security.NoSuchAlgorithmException;

public interface UserService extends IService<User> {

    public Boolean checkUsernameAndPasswordFromDB(User user);

}
