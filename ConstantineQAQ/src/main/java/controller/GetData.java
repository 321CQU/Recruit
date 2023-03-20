package controller;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONArray;
import com.alibaba.fastjson2.JSONObject;
import jdk.swing.interop.SwingInterOpUtils;
import pojo.Cache;
import pojo.Course;
import pojo.Score;
import utils.CacheUtils;
import utils.JsonUtils;
import utils.OkHttpUtils;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class GetData {
    /**
     * 获取成绩的对象集合
     * @throws IOException
     */
    public static List<Score> getScores() throws IOException {
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
//        List<Score> scores = JsonUtils.parseJson(jsonArray.toString());
        if (null == cache){
            scores=JsonUtils.parseJson(jsonArray.toString());
            cache = new Cache();
            cache.setKey(key);
            cache.setValue(scores);
            CacheUtils.putCache(key, cache);
        }else {
            scores=(List<Score>) cache.getValue();
        }
        //  return estateMapper.getAllEstateByUserId(submitUserId);
        return scores;
/*        for (Score score : scores) {
            Course course = score.getCourse();
            String courseName = course.getName();
            Integer courseScore = score.getScore();
        }*/
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
