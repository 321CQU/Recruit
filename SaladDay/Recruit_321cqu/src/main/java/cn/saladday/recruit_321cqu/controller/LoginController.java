package cn.saladday.recruit_321cqu.controller;


import cn.saladday.recruit_321cqu.common.CustomException;
import cn.saladday.recruit_321cqu.common.OptLog;
import cn.saladday.recruit_321cqu.common.R;
import cn.saladday.recruit_321cqu.dto.LoginDto;
import cn.saladday.recruit_321cqu.dto.TokenDto;
import cn.saladday.recruit_321cqu.entity.Token;
import cn.saladday.recruit_321cqu.entity.User;
import cn.saladday.recruit_321cqu.service.TokenService;
import cn.saladday.recruit_321cqu.service.UserService;
import cn.saladday.recruit_321cqu.utils.ThreadLocalEmpIdDataUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.models.auth.In;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.io.DataInput;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.LinkedHashMap;

@Api(tags = "token管理")
@Slf4j
@RestController
public class LoginController {

    @Autowired
    UserService userService;
    @Autowired
    TokenService tokenService;

    @Autowired
    RestTemplate restTemplate;

    @OptLog("获取新token")
    @ApiOperation("获取新token")
    @PostMapping("/login")
    public R getToken(@RequestBody LoginDto loginDto) throws NoSuchAlgorithmException {
        log.info("------通过授权服务器获取token------");
        ResponseEntity<R> rResponseEntity = restTemplate.postForEntity("https://api.321cqu.com/v1/authorization/login", loginDto, R.class);

        log.info("-----状态码{}-----",rResponseEntity.getStatusCode());
        if(rResponseEntity.getStatusCode()== HttpStatus.OK){
            //将用户名存入线程
            ThreadLocalEmpIdDataUtil.setUserId(loginDto.getUsername());
            //password进行md5加密
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] digest = md.digest(loginDto.getPassword().getBytes());
            BigInteger number = new BigInteger(1,digest);
            loginDto.setPassword(number.toString(16));

            //存储用户信息
            User user = new User();
            user.setUserId(loginDto.getUsername());
            user.setPassword(loginDto.getPassword());
            QueryWrapper<User> wrapper = new QueryWrapper<>();
            wrapper.eq("user_id",user.getUserId());
            //TODO 代码优化
            //save OR update
            User one = userService.getOne(wrapper);
            if(one==null){
                userService.save(user);
            }else{
                UpdateWrapper<User> wrapper1 = new UpdateWrapper<>();
                wrapper1.eq("user_id",user.getUserId())
                                .set("password",user.getPassword());
                userService.update(wrapper1);
            }
            //储存token信息
            Token token = new Token();
            token.setUserId(loginDto.getUsername());
            LinkedHashMap data = (LinkedHashMap) rResponseEntity.getBody().getData();
            token.setToken((String) data.get("token"));
            token.setRefreshToken((String) data.get("refreshToken"));
            token.setTokenExpireTime((Integer) data.get("tokenExpireTime"));
            token.setRefreshTokenExpireTime((Integer) data.get("refreshTokenExpireTime"));
            //save OR update
            QueryWrapper<Token> wrapper2 = new QueryWrapper<>();
            wrapper2.eq("user_id",token.getUserId());
            Token one1 = tokenService.getOne(wrapper2);
            if(one1==null){
                tokenService.save(token);
            }else{
                UpdateWrapper<Token> wrapper3 = new UpdateWrapper<>();
                wrapper3.eq("user_id",token.getUserId());
//                        .set("token",token.getToken())
//                        .set("refresh_token",token.getRefreshToken())
//                        .set("token_expire_time",token.getTokenExpireTime())
//                        .set("refresh_token_expire_time",token.getRefreshTokenExpireTime());
                tokenService.update(token,wrapper3);
            }

            return rResponseEntity.getBody();
        }else{
            throw new CustomException("鉴权错误");
        }

    }

    @OptLog("刷新token")
    @PostMapping("/refresh")
    public R refreshToken(@RequestBody HashMap<String,String> map){
        //传入一个refreshtoken，以此刷新token，
        //如果刷新成功，通过此来索引token，并且改其值
        log.info("------通过授权服务器刷新token------");
        String refreshToken = map.get("refreshToken");
        TokenDto tokenDto = new TokenDto();
        tokenDto.setRefreshToken(refreshToken);
        ResponseEntity<R> rResponseEntity = restTemplate.postForEntity("https://api.321cqu.com/v1/authorization/refreshToken", tokenDto, R.class);
        log.info("-----状态码{}-----",rResponseEntity.getStatusCode());
        if(rResponseEntity.getStatusCode()!=HttpStatus.OK){

            throw new CustomException("刷新服务器异常");
        }
        //将用户名存入线程
        QueryWrapper<Token> queryWrapper = new QueryWrapper<Token>();
        queryWrapper.eq("refresh_token",refreshToken);
        Token one = tokenService.getOne(queryWrapper);
        if(one==null){
            throw new CustomException("refreshToken过期");
        }
        ThreadLocalEmpIdDataUtil.setUserId(one.getUserId());

        LinkedHashMap data = (LinkedHashMap) rResponseEntity.getBody().getData();
        UpdateWrapper<Token> wrapper = new UpdateWrapper<>();
        wrapper.eq("refresh_token",refreshToken);
        wrapper.set("token",data.get("token"));
        wrapper.set("token_expire_time",data.get("tokenExpireTime"));
        tokenService.update(wrapper);
        return rResponseEntity.getBody();
    }


}
