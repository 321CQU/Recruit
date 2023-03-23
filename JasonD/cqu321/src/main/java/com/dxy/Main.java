package com.dxy;

import com.alibaba.fastjson2.JSONArray;
import com.alibaba.fastjson2.JSONObject;
import com.dxy.dao.CourseDao;
import com.dxy.dao.ScoreDao;
import com.dxy.dao.SessionDao;
import com.dxy.pojo.*;
import com.dxy.utils.CacheUtils;
import com.dxy.utils.OkHttpUtils;
import com.dxy.utils.SqliteUtils;
import org.apache.log4j.Logger;

import java.util.List;
import java.util.Scanner;

/**
 * @Author: JasonD
 * @date: 2023/3/16 19:42
 * @Description:
 */
public class Main {
    public static Token token = new Token();
    public static SessionDao sessionDao = new SessionDao();
    public static CourseDao courseDao = new CourseDao();
    public static ScoreDao scoreDao = new ScoreDao();
    public static Logger logger = Logger.getLogger(Main.class);

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Boolean flag = true, login = false;
        List<Score> scores = null;
        while (flag) {
            System.out.println("options: login、refresh、query、save、exit: ");
            String option = scanner.next();
            switch (option) {
                case "login":
                    System.out.print("input username: ");
                    String username = scanner.next();
                    System.out.print("input password: ");
                    String password = scanner.next();
                    if (login(username, password)) {
                        login = true;
                        System.out.println("login success!");
                    }
                    break;
                case "refresh":
                    if (login) {
                        refresh();
                        System.out.println("refresh token ok");
                    } else {
                        System.out.println("must login first!");
                    }
                    break;
                case "query":
                    if (login) {
                        scores = queryAllScores(token);
                        if (scores != null) {
                            for (Score score : scores) {
                                System.out.println(score);
                            }
                        } else {
                            System.out.println("none");
                        }
                    } else {
                        System.out.println("must login first!");
                    }
                    break;
                case "save":
                    if (scores != null) {
                        saveToDatabase(scores);
                    } else {
                        System.out.println("pls query data before you save it");
                    }
                    break;
                case "exit":
                    flag = false;
                    CacheUtils.clearAll();
                    SqliteUtils.close();
                    break;
                default:
                    break;
            }
        }
    }

    //登录方法
    public static Boolean login(String username, String password) {
        try {
            JSONObject loginJson = new JSONObject();
            loginJson.put("apiKey", token.getApiKey());
            loginJson.put("applyType", "Recruit");
            loginJson.put("username", username);
            loginJson.put("password", password);
            JSONObject response = OkHttpUtils.dePost("https://api.321cqu.com/v1/authorization/login", loginJson);
            JSONObject data = response.getJSONObject("data");
            token.setKey(data.get("token").toString());
            token.setTokenExpireTime(Integer.parseInt(data.get("tokenExpireTime").toString()));
            token.setRefreshTokenExpireTime(Integer.parseInt(data.get("refreshTokenExpireTime").toString()));
            return true;
        } catch (Exception e) {
            //开启日志记录
            logger.error(e.getMessage());
        }
        return false;
    }

    //刷新token
    public static void refresh() {
        try {
            String key = token.getKey();
            JSONObject refreshJson = new JSONObject();
            refreshJson.put("refreshToken", key);
            JSONObject response = OkHttpUtils.dePost("https://api.321cqu.com/v1/authorization/refreshToken", refreshJson);
            JSONObject data = response.getJSONObject("data");
            token.setTokenExpireTime(Integer.parseInt(data.get("tokenExpireTime").toString()));
        } catch (Exception e) {
            //开启日志记录
            logger.error(e.getMessage());
        }
    }

    //查询所有的分数
    public static List<Score> queryAllScores(Token token) {
        try {
            if (CacheUtils.isContain("scores")) {
                System.out.println("from cache");
                Cache cache = CacheUtils.get("scores");
                return (List<Score>) cache.getData();
            } else {
                System.out.println("no cache");
                String data = OkHttpUtils.doGet("https://api.321cqu.com/v1/recruit/score", token.getKey());
                JSONArray datas = JSONArray.parseArray(data);
                List<Score> scores = datas.toJavaList(Score.class);
                Cache cache = new Cache("scores", scores);
                CacheUtils.put("scores", cache);
                return scores;
            }
        } catch (Exception e) {
            //开启异常日志记录
            logger.error(e.getMessage());
        }
        return null;
    }

    //保存到数据库方法
    public static void saveToDatabase(List<Score> scores) {
        try {
            for (Score score : scores) {
                //保存到session表
                Session session = score.getSession();
                int sessionRet = sessionDao.addSession(session);
                //保存到course表
                Course course = score.getCourse();
                int sessionId = sessionDao.querySessionId(session.getYear(), session.getIs_autumn());
                course.setSessionId(sessionId);
                int courseRet = courseDao.addCourse(course);
                //保存到score表
                int scoreRet = scoreDao.addScore(score);
                //这里要保证三个都保存成功才能进行下一个保存
                if (sessionRet + courseRet + scoreRet >= 2) {
                    logger.info("数据保存成功！");
                    continue;
                } else {
                    throw new Exception("保存失败：请检查数据库的数据");
                }
            }
        } catch (Exception e) {
            //开启异常日志记录
            logger.error(e.getMessage());
        } finally {
            try {
                sessionDao.close();
                courseDao.close();
                scoreDao.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
