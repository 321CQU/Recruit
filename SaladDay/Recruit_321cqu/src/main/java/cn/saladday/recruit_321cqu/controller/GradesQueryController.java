package cn.saladday.recruit_321cqu.controller;

import cn.saladday.recruit_321cqu.common.CustomException;
import cn.saladday.recruit_321cqu.common.OptLog;
import cn.saladday.recruit_321cqu.common.R;
import cn.saladday.recruit_321cqu.dto.LoginDto;
import cn.saladday.recruit_321cqu.entity.Course;
import cn.saladday.recruit_321cqu.entity.Grades;
import cn.saladday.recruit_321cqu.entity.Token;
import cn.saladday.recruit_321cqu.entity.User;
import cn.saladday.recruit_321cqu.service.CourseService;
import cn.saladday.recruit_321cqu.service.GradesService;
import cn.saladday.recruit_321cqu.service.TokenService;
import cn.saladday.recruit_321cqu.service.UserService;
import cn.saladday.recruit_321cqu.utils.RedisUtils;
import cn.saladday.recruit_321cqu.utils.ThreadLocalEmpIdDataUtil;
import cn.saladday.recruit_321cqu.utils.formatBean.FormatBean;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.annotations.Api;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;


import java.net.URI;
import java.security.NoSuchAlgorithmException;
import java.sql.Timestamp;
import java.util.*;

@Slf4j
@Api(tags = "成绩查询")
@RestController
public class GradesQueryController {
    @Autowired
    UserService userService;
    @Autowired
    RedisUtils redisUtils;
    @Autowired
    LoginController loginController;
    @Autowired
    ObjectMapper objectMapper;
    @Autowired
    CourseService courseService;

    @Autowired
    GradesService gradesService;

    @Autowired
    TokenService tokenService;

    @Autowired
    RestTemplate restTemplate;

    @OptLog("获取成绩信息")
    @PostMapping("/gradesUaP")
    public R queryGradesByUaP(@RequestBody LoginDto loginDto) throws NoSuchAlgorithmException {
    //1.先查缓存
        //只有再密码正确的情况下查缓存和数据库，其他情况都需要去API查
        User loginUser = new User();
        loginUser.setUserId(loginDto.getUsername());
        loginUser.setPassword(loginDto.getPassword());
        Boolean checkUsernameAndPasswordFromDB;
        try {
            checkUsernameAndPasswordFromDB = userService.checkUsernameAndPasswordFromDB(loginUser);
        }catch (NullPointerException e){
            //空指针异常说明没有此账号；
            checkUsernameAndPasswordFromDB=false;
        }

        if(checkUsernameAndPasswordFromDB) {
            ThreadLocalEmpIdDataUtil.setUserId(loginDto.getUsername());

            FormatBean cache = (FormatBean) redisUtils.get(loginDto.getUsername());
            if (cache != null) {
                log.info("从缓存中读取数据");
                return R.success(cache);
            }

            //2.查询数据库
            FormatBean list = gradesService.queryAllGradesByUserId(loginDto.getUsername());
            if (list.getScores().size() != 0) {
                log.info("-----通过数据库返回数据-----");
                log.info("start-----存入数据缓存中-----");
                redisUtils.set(loginDto.getUsername(), list);
                log.info("end-----存入数据缓存中-----");
                return R.success(list);
            }
        }

    //3.发送请求并存储数据
        //1.查询数据库中是否有token
        QueryWrapper<Token> wrapper = new QueryWrapper<>();
        wrapper.eq("user_id",loginDto.getUsername());
        Token one = tokenService.getOne(wrapper);
        log.info("begin------校验//获取TOKEN-----");

        if(!checkUsernameAndPasswordFromDB||one==null){
            //如果没有token或者DB中的账号密码出错，需向服务器登入
            if(loginDto.getApiKey() == null || loginDto.getApplyType() ==null){
                throw new CustomException("本地账号密码错误，因为ApiKey或ApplyType为空无法向服务器校验");
            }
            R RToken = loginController.getToken(loginDto);
            LinkedHashMap data = (LinkedHashMap) RToken.getData();
            one = new Token();
            one.setUserId(loginDto.getUsername());
            one.setToken((String) data.get("token"));
            one.setRefreshToken((String) data.get("refreshToken"));
            one.setTokenExpireTime((Integer) data.get("tokenExpireTime"));
            one.setRefreshTokenExpireTime((Integer) data.get("refreshTokenExpireTime"));


        }else {
            //1.检查是否过期
            if (System.currentTimeMillis() / 1000 > one.getTokenExpireTime()) {
                //过期了，需要重新获取
                if (System.currentTimeMillis() / 1000 <= one.getRefreshTokenExpireTime()) {
                    HashMap<String, String> map = new HashMap<>();
                    map.put("refreshToken", one.getRefreshToken());
                    R r = loginController.refreshToken(map);
                    if (r.getStatus() == 0) {
                        throw new CustomException("调用refreshToken函数Error");
                    } else {
                        LinkedHashMap<String, Object> data = (LinkedHashMap<String, Object>) r.getData();
                        one.setToken((String) data.get("token"));
                        one.setTokenExpireTime((Integer) data.get("tokenExpireTime"));
                    }
                    //通过refreshToken获取
                } else {
                    throw new CustomException("refreshToken过期，请重新获取");
                }

            }
        }
        log.info("end------校验//获取TOKEN-----");


        log.info("begin-----调用查询成绩API-----");
        String url = "https://api.321cqu.com/v1/recruit/score";
        String authStr = "Bearer "+one.getToken();
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.USER_AGENT, "Mozilla/5.0 (Windows NT 10.0; Win64; x64) " +
                "AppleWebKit/537.36 (KHTML, like Gecko) Chrome/62.0.3202.94 Safari/537.36");
        headers.add("Authorization",authStr);
        HttpEntity<MultiValueMap<String,String>> request = new HttpEntity<>(null,
               headers);
        ResponseEntity<R> exchange = restTemplate.exchange(url, HttpMethod.GET, request, R.class);
        LinkedHashMap data = (LinkedHashMap) exchange.getBody().getData();
        ArrayList<LinkedHashMap> score = (ArrayList<LinkedHashMap>) data.get("scores");
        if(exchange.getStatusCode()!=HttpStatus.OK){
           throw new CustomException("查询成绩异常");
        }else{
            //存储入数据库
            for (LinkedHashMap o : score) {
                //1.存课程
                LinkedHashMap o1 = (LinkedHashMap) o.get("course");
                Course course = objectMapper.convertValue(o1, Course.class);
                UpdateWrapper<Course> wrapper1 = new UpdateWrapper();
                wrapper1.eq("name",course.getName());
                courseService.saveOrUpdate(course,wrapper1);
                //2.存成绩
                Grades grades = new Grades();
                LinkedHashMap o2 = (LinkedHashMap) o.get("session");
                grades.setUserId(one.getUserId());
                grades.setSessionId((String) o2.get("id"));
                grades.setSessionYear((Integer) o2.get("year"));
                grades.setSessionIsAutumn((Boolean) o2.get("is_autumn"));
                grades.setCourseName((String) o1.get("name"));
                grades.setScore((String) o.get("score"));
                grades.setStudyNature((String) o.get("study_nature"));
                grades.setCourseNature((String) o.get("course_nature"));

                //save Or update
                gradesService.updateOrSaveByMultiKey(grades);
                log.info("ee");
            }
            log.info("end-----调用查询成绩API-----");
            return exchange.getBody();

       }


    }
    @PostMapping("/gradesToken")
    public R<List<Grades>> queryGradesByToken(){
        //TODO 通过TOKEN直接查询成绩

        return null;
    }
}
