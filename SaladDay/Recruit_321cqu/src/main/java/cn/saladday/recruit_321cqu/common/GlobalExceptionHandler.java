package cn.saladday.recruit_321cqu.common;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.HttpClientErrorException;

import java.sql.SQLIntegrityConstraintViolationException;

/**
 * 全局异常处理器
 * @ControllerAdvice 控制层增强器
 *
 */
@ControllerAdvice
@Slf4j
@ResponseBody
public class GlobalExceptionHandler {
    //异常处理器的返回值类型需要和原方法的返回值类型相同
    @ExceptionHandler(HttpClientErrorException.class)
    public R repeatUsernameExceptionHandler(HttpClientErrorException e){
        String message = e.getMessage();
        return R.error("apiKey error:"+message);
    }

    @ExceptionHandler(CustomException.class)
    public R CouldNotDeleteExceptionHandler(CustomException e){
        String message = e.getMessage();
        return R.error(message);
    }

    @ExceptionHandler(Exception.class)
    public R repeatUsernameExceptionHandler(Exception e){
        String message = e.getMessage();
        return R.error("Known Error:"+message);
    }
}
