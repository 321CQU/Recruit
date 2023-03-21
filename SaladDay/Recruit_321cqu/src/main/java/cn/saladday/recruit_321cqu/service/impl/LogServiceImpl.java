package cn.saladday.recruit_321cqu.service.impl;

import cn.saladday.recruit_321cqu.dao.LogMapper;
import cn.saladday.recruit_321cqu.entity.Log;
import cn.saladday.recruit_321cqu.service.LogService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

@Service
public class LogServiceImpl extends ServiceImpl<LogMapper, Log> implements LogService {

}
