package com.dxy.test;

import com.alibaba.fastjson2.JSONArray;
import com.alibaba.fastjson2.JSONObject;
import com.dxy.Main;
import com.dxy.dao.CourseDao;
import com.dxy.dao.ScoreDao;
import com.dxy.dao.SessionDao;
import com.dxy.pojo.*;
import com.dxy.utils.CacheUtils;
import com.dxy.utils.OkHttpUtils;
import com.dxy.utils.SqliteUtils;
import org.junit.Test;

import java.sql.*;
import java.util.List;
import org.apache.log4j.Logger;

/**
 * @Author: JasonD
 * @date: 2023/3/14 14:59
 * @Description:
 */
public class MyTest {

    public static Token token = new Token();

    //OkHttpUtils登录测试
    @Test
    public void GetTokenTest() throws Exception {
        //登录信息的json
        JSONObject loginJson = new JSONObject();
        loginJson.put("apiKey", token.getApiKey());
        loginJson.put("applyType", "Recruit");
        loginJson.put("username", "202220021081t");
        loginJson.put("password", "123456");
        JSONObject response = OkHttpUtils.dePost("https://api.321cqu.com/v1/authorization/login", loginJson);
        JSONObject data = response.getJSONObject("data");
        token.setKey(data.get("token").toString());
        token.setTokenExpireTime(Integer.parseInt(data.get("tokenExpireTime").toString()));
        token.setRefreshTokenExpireTime(Integer.parseInt(data.get("refreshTokenExpireTime").toString()));
    }

    //OkHttpUtilstoken刷新测试
    @Test
    public void RefreshTokenTest() throws Exception {
        GetTokenTest();
        String key = token.getKey();
        JSONObject refreshJson = new JSONObject();
        refreshJson.put("refreshToken", key);
        JSONObject response = OkHttpUtils.dePost("https://api.321cqu.com/v1/authorization/refreshToken", refreshJson);
        JSONObject data = response.getJSONObject("data");
        token.setTokenExpireTime(Integer.parseInt(data.get("tokenExpireTime").toString()));
    }

    //OkHttpUtilsDoGetTest获取成绩信息
    @Test
    public void UtilsDoGetTest() throws Exception {
        GetTokenTest();
        System.out.println();
        String data = OkHttpUtils.doGet("https://api.321cqu.com/v1/recruit/score", token.getKey());
        JSONArray datas = JSONArray.parseArray(data);
        System.out.println(datas.toString());
        List<Score> scores = datas.toJavaList(Score.class);
        for (Score score : scores) {
            System.out.println(score);
        }
    }

    /*
        SQLite数据库相关测试
        sqliteutils工具类测试
        各表的SQL注入测试
     */
    //sqliteutilsTest
    @Test
    public void SqliteUitlsTest() throws Exception {
        Connection connection = SqliteUtils.getConnection();
        //增
        PreparedStatement statement = connection.prepareStatement("insert into user values (?, ?, ?, ?)");
        Object[] objects = {null, "abc", 23, 1};
        for (int i = 0; i < objects.length; i++) {
            statement.setObject(i+1, objects[i]);
        }
        int execute =  statement.executeUpdate();
        if (execute == 1) {
            System.out.println("insert ok");
        }
        statement.close();

        //查
        statement = connection.prepareStatement("select * from user");
        ResultSet set = statement.executeQuery();
        while (set.next()) {
            System.out.print(set.getInt(1) + " ");
            System.out.print(set.getString(2) + " ");
            System.out.print(set.getInt(3) + " ");
            if (set.getInt(4) == 1) {
                System.out.print("male");
            } else {
                System.out.print("female");
            }
            System.out.println();
        }
        statement.close();

        //删
        statement = connection.prepareStatement("delete from user where name = 'abc'");
        execute = statement.executeUpdate();
        System.out.println(execute);
        statement.close();

        //改
        statement = connection.prepareStatement("update user set name = ?, age = ?, sex = ? where id = ?");
        Object[] params = {"dxy", 12, 1, 1};
        for (int i = 0; i < params.length; i++) {
            statement.setObject(i+1, params[i]);
        }
        execute = statement.executeUpdate();
        System.out.println(execute);
        statement.close();

        //查
        statement = connection.prepareStatement("select * from user");
        set = statement.executeQuery();
        while (set.next()) {
            System.out.print(set.getInt(1) + " ");
            System.out.print(set.getString(2) + " ");
            System.out.print(set.getInt(3) + " ");
            if (set.getInt(4) == 1) {
                System.out.print("male");
            } else {
                System.out.print("female");
            }
            System.out.println();
        }

        statement.close();
        connection.close();
    }

    //SessionDaoTest
    @Test
    public void SessionDaoTest() throws Exception {
        SessionDao sessionDao = new SessionDao();
//        Session session = new Session(null, 2011, true);
//        int result = sessionDao.addSession(session);
//        System.out.println(result);//ok

//        Session session = new Session(1, 2023, false);
//        int result = sessionDao.updateSession(session);
//        System.out.println(result);//ok

//        Session session = sessionDao.querySession(1);
//        System.out.println(session);//ok

        int result = sessionDao.deleteSessionById(1);
        System.out.println(result);//ok
    }

    //CourseDaoTest
    @Test
    public void CourseDaoTest() throws Exception {
        CourseDao courseDao = new CourseDao();
//        Course course = new Course(101, "dxy", "java", 1.0F, "xxx01", 1);
//        int result = courseDao.addCourse(course);
//        System.out.println(result);//ok

//        Course course = new Course(101, "ljj", "x++", 2.0F, "xxx02", 2);
//        int result = courseDao.updateCourse(course);
//        System.out.println(result);//ok

//        Course course = courseDao.queryCourseByCode(101);
//        System.out.println(course);//ok

        int result = courseDao.deleteCourseByCode(101);
        System.out.println(result);
    }

    //ScoreDaoTest
    @Test
    public void ScoreDaoTest() throws Exception {
        Course course = new Course(101, "dxy", "java", 1.0F, "xxx01", 1);
        Session session = new Session(null, 2012, true);
        ScoreDao scoreDao = new ScoreDao();
//        Score score = new Score(null, 98, "cx", "bx", course, session);
//        int result = scoreDao.addScore(score);
//        System.out.println(result);//ok

//        Score score = new Score(1, 80, "cx", "bx", course, session);
//        int result = scoreDao.updateScore(score);
//        System.out.println(result);//ok

//        Score score = scoreDao.queryScoreById(1);
//        System.out.println(score);//ok

        int result = scoreDao.deleteScoreById(1);
        System.out.println(result);//ok
    }

    //缓存查询测试ok
    @Test
    public synchronized void CacheUtilTest() throws Exception {
        GetTokenTest();
        //这里模拟了多次查询，间隔 DEFAULT_TIMEOUT+1 后再查询的缓存效果
        String[] inputs = {"1", "1", "1", "2", "1", "1"};
        for (String input : inputs) {
            switch (input) {
                case "1":
                    List<Score> scores = Main.queryAllScores(token);
                    break;
                case "2":
                    wait(18001);
                    break;
                default:
                    break;
            }
        }
        CacheUtils.clearAll();
    }

    //数据保存测试ok
    @Test
    public void queryScoreAll() throws Exception {
        GetTokenTest();
        List<Score> scores = Main.queryAllScores(token);
        Main.saveToDatabase(scores);
    }
}
