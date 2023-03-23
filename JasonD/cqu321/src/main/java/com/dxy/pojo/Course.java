package com.dxy.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author: JasonD
 * @date: 2023/3/16 17:37
 * @Description:
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Course {
    private Integer code;
    private String instructor;
    private String name;
    private Float credit;
    private String course_num;
    private Integer sessionId;
}
