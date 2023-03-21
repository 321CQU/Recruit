package cn.saladday.recruit_321cqu;

import cn.saladday.recruit_321cqu.dao.UserMapper;
import cn.saladday.recruit_321cqu.entity.Grades;
import cn.saladday.recruit_321cqu.entity.Token;
import cn.saladday.recruit_321cqu.entity.User;
import cn.saladday.recruit_321cqu.service.GradesService;
import cn.saladday.recruit_321cqu.service.TokenService;
import cn.saladday.recruit_321cqu.service.UserService;
import cn.saladday.recruit_321cqu.utils.RedisClient;
import cn.saladday.recruit_321cqu.utils.RedisUtils;
import cn.saladday.recruit_321cqu.utils.formatBean.FormatBean;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@SpringBootTest
class Recruit321cquApplicationTests {
    @Autowired
    UserService userService;
    @Autowired
    TokenService tokenService;
    @Autowired
    GradesService gradesService;
    @Autowired
    RedisUtils redisUtils;


    @Test
    void testUserService() {
        User u1 = new User();
        u1.setUserId("111");
//        u1.setPassword("");
        userService.save(u1);
    }

    @Test
    void testTokenService(){
        Token t1 = new Token();
        t1.setUserId("141");
        t1.setToken("");
        t1.setRefreshTokenExpireTime(10000);
        t1.setRefreshToken("");
        t1.setRefreshTokenExpireTime(200000);
        tokenService.save(t1);
    }

    @Test
    void testSelectList(){
        FormatBean formatBean = gradesService.queryAllGradesByUserId("121");
    }

    @Test
    void testJedis(){
        Token token = new Token();
        token.setToken("");
        token.setRefreshToken("");
        redisUtils.set("B",token);
    }

    @Test
    void testJedis2(){

        Token a = (Token) redisUtils.get("C");
        System.out.printf(String.valueOf(a));
    }

    @Test
    void testCheckUsernameAndPasswordFromDB(){
        User user = new User();
        user.setPassword("");
        user.setUserId("");
        Boolean aBoolean;
        try {
            aBoolean = userService.checkUsernameAndPasswordFromDB(user);
        }catch (NullPointerException e){
            aBoolean = false;
        }

        System.out.println(aBoolean);
    }

}
