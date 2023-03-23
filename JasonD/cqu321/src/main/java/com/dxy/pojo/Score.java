package com.dxy.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

/**
 * @Author: JasonD
 * @date: 2023/3/15 09:35
 * @Description:
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Score {
    private Integer id;
    private Integer score;
    private String study_nature;
    private String course_nature;
    private Course course;
    private Session session;
}
