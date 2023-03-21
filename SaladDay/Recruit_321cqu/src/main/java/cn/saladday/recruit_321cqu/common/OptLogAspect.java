package cn.saladday.recruit_321cqu.common;


import cn.saladday.recruit_321cqu.entity.Log;
import cn.saladday.recruit_321cqu.service.LogService;
import cn.saladday.recruit_321cqu.utils.ThreadLocalEmpIdDataUtil;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.time.LocalDateTime;

/*
    切面类
 */
@Aspect
@Component
@Slf4j
public class OptLogAspect {
    @Autowired
    LogService logService;

    @Pointcut("@annotation( cn.saladday.recruit_321cqu.common.OptLog )")
    public void logPointCut(){
    }


    /*
        当pointCut正常被执行，返回的时候执行此切面
     */
    @AfterReturning("logPointCut()")
    public void saveSysLog(JoinPoint joinPoint){
        OptLogAspect.log.info("start-----aop-log-----");
        Log log = new Log();
        //从织入点获取方法
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();

        //获取注解信息
        cn.saladday.recruit_321cqu.common.OptLog annotation = method.getAnnotation(cn.saladday.recruit_321cqu.common.OptLog.class);
        if(annotation!=null){
            String value = annotation.value();
            log.setOperate(value);
        }
        //方法名
        String name = method.getName();
        log.setMethod(name);
        //
        String userId = ThreadLocalEmpIdDataUtil.getUserId();
        if(userId == null){
            throw new CustomException("记录操作日志异常");
        }
        log.setUserId(userId);

        //operateTime
        log.setOperateTime(LocalDateTime.now());
        try {
            logService.save(log);
        }catch (Exception e) {
            throw new CustomException("记录操作日志异常:" + e.getMessage());
        }

    }
}
