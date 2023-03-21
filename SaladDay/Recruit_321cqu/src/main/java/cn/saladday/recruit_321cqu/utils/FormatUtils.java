package cn.saladday.recruit_321cqu.utils;

import cn.saladday.recruit_321cqu.common.CustomException;
import cn.saladday.recruit_321cqu.entity.Course;
import cn.saladday.recruit_321cqu.entity.Grades;
import cn.saladday.recruit_321cqu.service.CourseService;
import cn.saladday.recruit_321cqu.service.impl.CourseServiceImpl;
import cn.saladday.recruit_321cqu.utils.formatBean.FormatBean;
import cn.saladday.recruit_321cqu.utils.formatBean.FormatCourseBean;
import cn.saladday.recruit_321cqu.utils.formatBean.FormatGradeBean;
import cn.saladday.recruit_321cqu.utils.formatBean.FormatSessionBean;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.aop.scope.ScopedProxyUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class FormatUtils {

    @Autowired
    CourseService courseService;

    //将数据库查出的格式转化为发送的格式
    public  FormatBean format2json(List<Grades> rawList){
        ObjectMapper objectMapper = new ObjectMapper();
        FormatBean formatBean = new FormatBean();
        ArrayList<FormatGradeBean> scores = new ArrayList<>();
        for (Grades grades : rawList) {
            FormatGradeBean gradeBean = new FormatGradeBean();
            FormatSessionBean sessionBean = new FormatSessionBean();
            sessionBean.setId(grades.getSessionId());
            sessionBean.setYear(grades.getSessionYear());
            sessionBean.setIs_autumn(grades.getSessionIsAutumn());
            gradeBean.setSession(sessionBean);

            //通过courseName查询course并且插入

            QueryWrapper<Course> wrapper1 = new QueryWrapper<>();
            wrapper1.eq("name",grades.getCourseName());

            Course one = courseService.getOne(wrapper1);
            FormatCourseBean courseBean = objectMapper.convertValue(one, FormatCourseBean.class);
            gradeBean.setCourse(courseBean);

//            FormatCourseBean courseBean = new FormatCourseBean();
//            courseBean.setName(one.getName());
//            courseBean.setCode(one.getCode());
//            courseBean.setCourse_num(one.getCourse_num());
//            courseBean.setDept(one.getDept());
//            courseBean.setCredit(on


            gradeBean.setScore(grades.getScore());
            gradeBean.setStudy_nature(grades.getStudyNature());
            gradeBean.setCourse_nature(grades.getCourseNature());
            scores.add(gradeBean);
        }
        formatBean.setScores(scores);
        return formatBean;
    }
}
