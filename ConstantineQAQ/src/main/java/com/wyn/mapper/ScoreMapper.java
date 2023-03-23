package com.wyn.mapper;

import com.wyn.pojo.Course;
import com.wyn.pojo.Score;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface ScoreMapper {

    /**
     * 添加所有成绩
     * @param course
     */
    @Insert("insert into course values(#{code},#{instructor},#{name},#{credit},#{course_num},#{sessionId})")
    @ResultMap("courseResultMap")
    void addCourse(Course course);

    @Delete("delete from course")
    void deleteAll();

/*    @Insert("insert into score values(#{id},#{score},#{study_nature},#{course_nature},#{course})")
    @ResultMap("scoreResultMap")
    void addScore(Score score);*/


    /**
     *
     * @return 查询所有成绩对象
     */
    @Select("select * from score")
    List<Score> selectAll();
}
