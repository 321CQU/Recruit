package cn.saladday.recruit_321cqu.service.impl;

import cn.saladday.recruit_321cqu.dao.GradesMapper;
import cn.saladday.recruit_321cqu.entity.Course;
import cn.saladday.recruit_321cqu.entity.Grades;
import cn.saladday.recruit_321cqu.entity.User;
import cn.saladday.recruit_321cqu.service.CourseService;
import cn.saladday.recruit_321cqu.service.GradesService;
import cn.saladday.recruit_321cqu.utils.FormatUtils;
import cn.saladday.recruit_321cqu.utils.formatBean.FormatBean;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.conditions.query.QueryChainWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

@Service
public class GradesServiceImpl extends ServiceImpl<GradesMapper, Grades> implements GradesService {
    @Autowired
    CourseService courseService;
    @Autowired
    FormatUtils formatUtils;
    @Override
    public boolean updateOrSaveByMultiKey(Grades grades) {
        //select
        QueryWrapper<Grades> wrapper = new QueryWrapper<>();
        wrapper.eq("user_id",grades.getUserId())
                .eq("course_name",grades.getCourseName());
        Grades one = this.getOne(wrapper);
        try {
            if(one==null){
                //save
                this.save(grades);
                return true;
            }else {
                //update
                UpdateWrapper<Grades> wrapper1 = new UpdateWrapper<>();
                wrapper1.eq("user_id",grades.getUserId())
                        .eq("course_name",grades.getCourseName());
                this.update(grades,wrapper1);
                return true;
            }
        }catch (Exception e){
            log.error(e.getMessage());
            return false;
        }
    }

    @Override
    public FormatBean queryAllGradesByUserId(String UserId) {
        QueryWrapper<Grades> wrapper = new QueryWrapper<>();
        wrapper.eq("user_id",UserId);
        List<Grades> list = this.list(wrapper);
        FormatBean formatBean = formatUtils.format2json(list);
        return formatBean;
    }
}
