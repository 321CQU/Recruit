package com.dxy.dao;

import com.dxy.pojo.Course;
import com.dxy.utils.SqliteUtils;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 * @Author: JasonD
 * @date: 2023/3/16 17:59
 * @Description:
 */
public class CourseDao {
    PreparedStatement statement;

    //增
    public int addCourse(Course course) {
        try {
            statement = SqliteUtils.getConnection().prepareStatement("insert into course values (?, ?, ?, ?, ?, ?)");
            Object[] params = {course.getCode(), course.getInstructor(), course.getName(), course.getCredit(), course.getCourse_num(), course.getSessionId()};
            for (int i = 0; i < params.length; i++) {
                statement.setObject(i + 1, params[i]);
            }
            int add = statement.executeUpdate();
            return add;
        } catch (Exception e) {
            return 0;
        }
    }

    //删
    public int deleteCourseByCode(Integer code) {
        try {
            statement = SqliteUtils.getConnection().prepareStatement("delete from course where code = ?");
            statement.setObject(1, code);
            int delete = statement.executeUpdate();
            return delete;
        } catch (Exception e) {
            return 0;
        }
    }

    //改
    public int updateCourse(Course course) {
        try {
            statement = SqliteUtils.getConnection().prepareStatement("update course set instructor = ?, name = ?, credit = ?, course_num = ?, session_id = ? where code = ?");
            Object[] params = {course.getInstructor(), course.getName(), course.getCredit(), course.getCourse_num(), course.getSessionId(), course.getCode()};
            for (int i = 0; i < params.length; i++) {
                statement.setObject(i + 1, params[i]);
            }
            int update = statement.executeUpdate();
            return update;
        } catch (Exception e) {
            return 0;
        }
    }

    //查
    public Course queryCourseByCode(Integer code) throws Exception {
        statement = SqliteUtils.getConnection().prepareStatement("select * from course where code = ?");
        statement.setObject(1, code);
        ResultSet resultSet = statement.executeQuery();
        int id = resultSet.getInt(1);
        String instructor = resultSet.getString(2);
        String name = resultSet.getString(3);
        Float credit = resultSet.getFloat(4);
        String course_num = resultSet.getString(5);
        Course course = new Course(id, instructor, name, credit, course_num, resultSet.getInt(6));
        return course;
    }

    public void close() throws Exception {
        statement.close();
    }

}
