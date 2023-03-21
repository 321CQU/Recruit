package cn.saladday.recruit_321cqu.utils.formatBean;

import lombok.Data;

@Data
public class FormatGradeBean {

    FormatSessionBean session;
    FormatCourseBean course;
    String score;
    String study_nature;
    String course_nature;
}
