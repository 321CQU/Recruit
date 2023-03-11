package controller;

import com.alibaba.fastjson2.JSONArray;
import com.alibaba.fastjson2.JSONObject;
import utils.OkHttpApi;

import java.io.IOException;

public class GetToken {
    public static String getToken() throws IOException {
        OkHttpApi api = new OkHttpApi();
        JSONObject object = new JSONObject(){{
            put("username","07099115");
            put("password","123456789");
            put("apiKey","hLrOSgvl5eUzJR1sSeen9g");
            put("applyType","Recruit");
        }};
        String s = object.toString();
        String post = api.post("https://api.321cqu.com/v1/authorization/login", s);
        JSONObject jsonObject = JSONObject.parseObject(post);
        JSONArray data = jsonObject.getJSONArray("data");
        Object token = data.getJSONObject(0).get("token");
        return token.toString();
    }

}
