package cn.saladday.recruit_321cqu.utils;

public class ThreadLocalEmpIdDataUtil {

    public static ThreadLocal<String> threadLocal = new ThreadLocal<String>();


    public static String getUserId(){
        return threadLocal.get();
    }
    public static void setUserId(String UserId){
        threadLocal.set(UserId);
    }


}
