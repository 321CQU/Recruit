package com.wyn.service;

import com.wyn.pojo.Course;
import com.wyn.pojo.Score;

import java.util.List;

public interface ScoreService {


    void addCourse(Course course);

    void deleteAll();

    void addScore(Score score);

    List<Score> selectAll();
}
