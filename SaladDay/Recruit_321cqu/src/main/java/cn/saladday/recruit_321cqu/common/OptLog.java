package cn.saladday.recruit_321cqu.common;


import java.lang.annotation.*;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface OptLog {
    String value() default "";
}
