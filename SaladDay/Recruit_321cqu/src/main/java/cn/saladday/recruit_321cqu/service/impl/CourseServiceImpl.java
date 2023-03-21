package cn.saladday.recruit_321cqu.service.impl;

import cn.saladday.recruit_321cqu.dao.CourseMapper;
import cn.saladday.recruit_321cqu.entity.Course;
import cn.saladday.recruit_321cqu.service.CourseService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

@Service
public class CourseServiceImpl extends ServiceImpl<CourseMapper, Course> implements CourseService {
}
