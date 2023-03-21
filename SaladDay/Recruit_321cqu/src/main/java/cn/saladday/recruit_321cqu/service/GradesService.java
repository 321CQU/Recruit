package cn.saladday.recruit_321cqu.service;

import cn.saladday.recruit_321cqu.dao.GradesMapper;
import cn.saladday.recruit_321cqu.entity.Grades;
import cn.saladday.recruit_321cqu.utils.formatBean.FormatBean;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

public interface GradesService extends IService<Grades> {
    boolean updateOrSaveByMultiKey(Grades grades);
    FormatBean queryAllGradesByUserId(String UserId);
}
