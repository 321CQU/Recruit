package com.dxy.utils;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONArray;
import com.alibaba.fastjson2.JSONObject;
import okhttp3.*;
import org.apache.log4j.Logger;

import java.io.IOException;

/**
 * @Author: JasonD
 * @date: 2023/3/14 11:12
 * @Description:
 */
public class OkHttpUtils {
    public static Logger logger = Logger.getLogger(OkHttpUtils.class);

    public static OkHttpClient client  = new OkHttpClient();

    //修改请求内容类型
    public static final MediaType MT = MediaType.get("application/json");

    //Post请求
    public static JSONObject dePost(String url, JSONObject jsonObject) throws IOException {
        RequestBody requestBody = RequestBody.create(jsonObject.toString(), MT);
        Request request = new Request.Builder()
                .url(url)
                .post(requestBody)
                .build();
        Call call = client.newCall(request);
        Response response = call.execute();
        JSONObject result = JSON.parseObject(response.body().string());
        Integer status = result.getInteger("status");
        String msg = result.getString("msg");
        logging(url + " status: " + status + " msg: " + msg);
        return result;
    }

    //Get请求
    public static String doGet(String url, String token) throws IOException {
        Request request = new Request.Builder()
                .url(url)
                .addHeader("Authorization", "Bearer " + token)
                .build();
        Call call = client.newCall(request);
        Response response = call.execute();
        JSONObject jsonObject = JSON.parseObject(response.body().string());
        Integer status = jsonObject.getInteger("status");
        String msg = jsonObject.getString("msg");
        JSONArray scorces = jsonObject.getJSONObject("data").getJSONArray("scores");
        logging(url + " status: " + status + " msg: " + msg);
        return scorces.toString();
    }

    //api接口访问日志设置
    public static void logging(String message) {
        logger.info(message);
    }
}
