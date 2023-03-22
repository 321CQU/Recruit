package com.wyn.controller;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONArray;
import com.alibaba.fastjson2.JSONObject;
import com.wyn.pojo.Course;
import com.wyn.pojo.Score;
import com.wyn.service.Impl.ScoreServiceImpl;
import lombok.extern.slf4j.Slf4j;
import com.wyn.pojo.Cache;
import com.wyn.utils.CacheUtils;
import com.wyn.utils.JsonUtils;
import com.wyn.utils.OkHttpUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

@Slf4j
public class GetData {
    /**
     * 获取成绩的对象集合
     * @throws IOException
     */
    public static final Logger logger= LoggerFactory.getLogger(GetData.class);


    public static List<Score> getScores() throws IOException {
        ScoreServiceImpl scoreService = new ScoreServiceImpl();
        String key= CacheUtils.findAllScore;
        List<Score> scores;
        OkHttpUtils api = new OkHttpUtils();
        String run = api.run("https://api.321cqu.com/v1/recruit/score");
        //防止乱码
        byte[] bytes = run.getBytes(StandardCharsets.UTF_8);
        String jsonStringWithCharset = new String(bytes, StandardCharsets.ISO_8859_1);
        JSONObject jsonObject = JSON.parseObject(jsonStringWithCharset);
        JSONArray jsonArray = jsonObject.getJSONObject("data").getJSONArray("scores");
        Cache cache = CacheUtils.getCacheInfo(key);
        if (null == cache){
            scores=JsonUtils.parseJson(jsonArray.toString());
            cache = new Cache();
            cache.setKey(key);
            cache.setValue(scores);
            CacheUtils.putCache(key, cache);
/*            for(Score score : scores){
                scoreService.addScore(score);
            }*/
        }else {
            scores=(List<Score>) cache.getValue();
        }
        return scores;
    }

    public static List<Course> getCourse() throws IOException{
        List<Score> scores = getScores();
        List<Course> courses = new ArrayList<>();
        for (Score score : scores){
            Course course = score.getCourse();
            courses.add(course);
        }
        return courses;
    }

    /**
     *
     * @return 课程名称
     * @throws IOException
     */
    public static List<String> getCourseName() throws IOException {
        List<Score> scores = getScores();
        List<String> coursesNames = new ArrayList<>();
        for (Score score : scores){
            Course course = score.getCourse();
            String courseName = course.getName();
            coursesNames.add(courseName);
        }
        return coursesNames;
    }

    /**
     *
     * @return 课程成绩
     * @throws IOException
     */
    public static List<Integer> getCourseScore() throws IOException{
        List<Score> scores = getScores();
        List<Integer> courseScores = new ArrayList<>();
        for (Score score : scores){
            Integer courseScore = score.getScore();
            courseScores.add(courseScore);
        }
        return courseScores;
    }

    /**
     *
     * @return 课程指导老师
     * @throws IOException
     */
    public static List<String> getCourseInstructor() throws IOException{
        List<Score> scores = getScores();
        List<String> Instructors = new ArrayList<>();
        for (Score score : scores){
            Course course = score.getCourse();
            String instructor = course.getInstructor();
            Instructors.add(instructor);
        }
        return Instructors;
    }

    /**
     *
     * @return 课程类型（必修or选修）
     * @throws IOException
     */
    public static List<String> getCourseNature() throws IOException{
        List<Score> scores = getScores();
        List<String> courseNatures = new ArrayList<>();
        for (Score score : scores){
            String courseNature = score.getCourse_nature();
            courseNatures.add(courseNature);
        }
        return courseNatures;
    }


}
