package cn.saladday.recruit_321cqu.dao;

import cn.saladday.recruit_321cqu.entity.Grades;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.fasterxml.jackson.databind.ser.Serializers;
import org.apache.ibatis.annotations.Mapper;


@Mapper
public interface GradesMapper extends BaseMapper<Grades> {
}
