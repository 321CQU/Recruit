package com.dxy.dao;

import com.dxy.pojo.Course;
import com.dxy.pojo.Score;
import com.dxy.pojo.Session;
import com.dxy.utils.SqliteUtils;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 * @Author: JasonD
 * @date: 2023/3/16 17:59
 * @Description:
 */
public class ScoreDao {
    PreparedStatement statement;

    //增
    public int addScore(Score score) {
        try {
            statement = SqliteUtils.getConnection().prepareStatement("insert into score values (?, ?, ?, ?, ?)");
            Object[] params = {null, score.getScore(), score.getStudy_nature(), score.getCourse_nature(), score.getCourse().getCode()};
            for (int i = 0; i < params.length; i++) {
                statement.setObject(i+1, params[i]);
            }
            int add = statement.executeUpdate();
            return add;
        } catch (Exception e) {
            return 0;
        }
    }

    //删
    public int deleteScoreById(Integer id) {
        try {
            statement = SqliteUtils.getConnection().prepareStatement("delete from score where id = ?");
            statement.setObject(1, id);
            int delete = statement.executeUpdate();
            return delete;
        } catch (Exception e) {
            return 0;
        }
    }

    //改
    public int updateScore(Score score) {
        try {
            statement = SqliteUtils.getConnection().prepareStatement("update score set score = ?, course_nature = ?, course_code = ? where id = ?");
            Object[] params = {score.getScore(), score.getCourse_nature(), score.getCourse().getCode(), score.getId()};
            for (int i = 0; i < params.length; i++) {
                statement.setObject(i+1, params[i]);
            }
            int update = statement.executeUpdate();
            return update;
        } catch (Exception e) {
            return 0;
        }
    }

    //查
    public Score queryScoreById(Integer id) throws Exception {
        statement = SqliteUtils.getConnection().prepareStatement("select * from score where id = ?");
        statement.setObject(1, id);
        ResultSet resultSet = statement.executeQuery();
        Integer score = resultSet.getInt(2);
        String study_nature = resultSet.getString(3);
        String course_nature = resultSet.getString(4);
        Course course = new CourseDao().queryCourseByCode(resultSet.getInt(5));
        Session session = new SessionDao().querySession(course.getSessionId());
        Score scoreObj = new Score(id, score, study_nature, course_nature, course, session);
        return scoreObj;
    }

    public void close() throws Exception {
        statement.close();
    }
}
